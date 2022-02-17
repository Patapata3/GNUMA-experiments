package org.unibayreuth.gnumaexperiments.handlers.messagehandling.handlers;

import com.google.gson.Gson;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.commands.experiments.UpdateExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.TrainingUpdateDTO;
import org.unibayreuth.gnumaexperiments.dto.MetricDTO;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.workers.ExperimentWorker;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveClassifierModelExperimentQuery;
import org.unibayreuth.gnumaexperiments.service.RequestSenderServiceBean;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;
import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@Component
public class TrainingUpdateHandler implements MessageHandler {
    private final Logger log = LoggerFactory.getLogger(TrainingUpdateHandler.class);
    private static final String TYPE = "TrainingUpdate";

    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private RequestSenderServiceBean requestSenderService;
    @Autowired
    private ExperimentWorker experimentWorker;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String getType() {
        return TYPE;
    }

    @Value("${axon.amqp.exchange}")
    private String exchange;

    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody());
        log(log::info, String.format("Started handling of an experiment update message, message body:\n%s", messageBody));
        Gson gson = new Gson();
        TrainingUpdateDTO experimentUpdate = gson.fromJson(messageBody, TrainingUpdateDTO.class);

        log(log::debug, String.format("Looking for an experiment on a classifier at {%s} and model {%s}",
                experimentUpdate.getAddress(), experimentUpdate.getModelId()));
        ExperimentView runningExperiment = queryGateway.query(new RetrieveClassifierModelExperimentQuery(experimentUpdate.getAddress(), experimentUpdate.getModelId()),
                instanceOf(ExperimentView.class)).join();
        if (runningExperiment == null) {
            log(log::error, String.format("Found no experiment for address=%s and modelId=%s",
                    experimentUpdate.getAddress(), experimentUpdate.getModelId()));
            return;
        }

        ExperimentClassifier runningClassifier = experimentWorker.getClassifierByAddress(runningExperiment,
                experimentUpdate.getAddress());
        if (runningClassifier == null) {
            log(log::error, String.format("Found no classifier in the experiment for address=%s", experimentUpdate.getAddress()));
            return;
        }

        ExperimentStatus newStatus = experimentUpdate.isFinished() ? ExperimentStatus.TEST : ExperimentStatus.TRAIN;
        Map<String, Double> newResults = Objects.isNull(experimentUpdate.getMetrics()) ? new HashMap<>() :
                experimentUpdate.getMetrics()
                        .stream()
                        .collect(Collectors.toMap(MetricDTO::getKey, MetricDTO::getValue));
        var updateCommand = new UpdateExperimentCommand(runningExperiment.getId(), runningClassifier.getId(), newStatus,
                newResults, experimentUpdate.getCurrentStep(), experimentUpdate.getTotalSteps());

        commandGateway.send(updateCommand);
        rabbitTemplate.convertAndSend(exchange, GNUMAConstants.ROUTING_KEY, updateCommand, m -> {
            m.getMessageProperties().setHeader("event", "ExperimentTrainingUpdate");
            return m;
        });

        if (newStatus == ExperimentStatus.TEST) {
            try {
                requestSenderService.sendPostRequest(String.format("%s/evaluate/%s", runningClassifier.getAddress(), experimentUpdate.getModelId()),
                        String.format("{doc_ids: %s}", gson.toJson(runningExperiment.getData().getDataSplit().getTestData())));
            } catch (IOException | InterruptedException | ServiceRequestException e) {
                log(log::error, e.getMessage(), e);
            }
        }
    }
}
