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
import org.unibayreuth.gnumaexperiments.dto.EvalFinishDTO;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.workers.ExperimentWorker;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveClassifierModelExperimentQuery;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;
import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@Component
public class EvaluationFinishedHandler implements MessageHandler {
    private static final String TYPE = "EvaluationFinished";
    private final Logger log = LoggerFactory.getLogger(EvaluationFinishedHandler.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private ExperimentWorker experimentWorker;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${axon.amqp.exchange}")
    private String exchange;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void handle(Message message) {
        String messageBody = new String(message.getBody());
        log(log::info, String.format("Started processing of evaluation finish message, message body:\n%s", messageBody));

        EvalFinishDTO evalFinishDTO = new Gson().fromJson(messageBody, EvalFinishDTO.class);
        log(log::debug, String.format("Looking for an experiment on classifier {%s} and model {%s}",
                evalFinishDTO.getClassifierId(), evalFinishDTO.getModelId()));
        ExperimentView runningExperiment = queryGateway.query(new RetrieveClassifierModelExperimentQuery(evalFinishDTO.getAddress(), evalFinishDTO.getModelId()), instanceOf(ExperimentView.class)).join();
        if (Objects.isNull(runningExperiment)) {
            log(log::error, String.format("Experiment for classifier {%s} model {%s} not found",
                    evalFinishDTO.getClassifierId(), evalFinishDTO.getModelId()));
            return;
        }

        ExperimentClassifier runningClassifier = experimentWorker.getClassifierByAddress(runningExperiment,
                evalFinishDTO.getAddress());
        if (runningClassifier == null) {
            log(log::error, String.format("Found no classifier in the experiment for classifierId=%s, address=%s",
                    evalFinishDTO.getClassifierId(), evalFinishDTO.getAddress()));
            return;
        }

        Map<String, Double> testResultMap = Objects.isNull(evalFinishDTO.getMetrics()) ? new HashMap<>() :
                evalFinishDTO.getMetrics()
                        .stream()
                        .collect(HashMap::new,
                                (map, metric) -> map.put(metric.getKey(), metric.getValue()),
                                HashMap::putAll);

        log(log::debug, "Updating experiment after evaluation was finished");
        var updateCommand = new UpdateExperimentCommand(runningExperiment.getId(), runningClassifier.getId(), ExperimentStatus.FINISH, testResultMap,
                evalFinishDTO.getResultSourceId(), evalFinishDTO.getResultSourceType());
        commandGateway.send(updateCommand);
        rabbitTemplate.convertAndSend(exchange, GNUMAConstants.ROUTING_KEY, new Gson().toJson(updateCommand), m -> {
            m.getMessageProperties().setHeader("event", "ExperimentTestFinish");
            return m;
        });

        log(log::info, "Experiment successfully finished");
    }
}