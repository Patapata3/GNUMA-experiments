package org.unibayreuth.gnumaexperiments.handlers.messagehandling.handlers;

import com.google.gson.Gson;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.experiments.UpdateExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.EvalFinishDTO;
import org.unibayreuth.gnumaexperiments.handlers.messagehandling.MessageHandler;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveClassifierModelExperimentQuery;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@Component
public class EvaluationFinishedHandler implements MessageHandler {
    private static final String TYPE = "EvaluationFinished";
    private final Logger log = LoggerFactory.getLogger(EvaluationFinishedHandler.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

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
        ExperimentView runningExperiment = queryGateway.query(new RetrieveClassifierModelExperimentQuery(evalFinishDTO.getClassifierId(),
                evalFinishDTO.getModelId()), ExperimentView.class).join();
        if (Objects.isNull(runningExperiment)) {
            log(log::error, String.format("Experiment for classifier {%s} model {%s} not found",
                    evalFinishDTO.getClassifierId(), evalFinishDTO.getModelId()));
            return;
        }

        log(log::debug, "Adding prefix \"test\" to result keys");
        Map<String, Double> testResultMap = Objects.isNull(evalFinishDTO.getResults()) ? new HashMap<>() :
                evalFinishDTO.getResults().keySet()
                        .stream()
                        .collect(HashMap::new,
                                (map, key) -> map.put("test_" + key, evalFinishDTO.getResults().get(key)),
                                HashMap::putAll);

        log(log::debug, "Updating experiment after evaluation was finished");
        commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), ExperimentStatus.FINISH, testResultMap,
                evalFinishDTO.getResultSourceId(), evalFinishDTO.getResultSourceType()));
        log(log::info, "Experiment successfully finished");
    }
}