package org.unibayreuth.gnumaexperiments.handlers.messagehandling.workers;

import com.google.common.base.Objects;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.experiments.UpdateExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveClassifierModelExperimentQuery;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import javax.annotation.Nullable;
import java.util.UUID;

import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.log;

@Component
public class ExperimentWorker {
    private Logger log = LoggerFactory.getLogger(ExperimentWorker.class);

    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private CommandGateway commandGateway;

    @Nullable
    public ExperimentClassifier getClassifierByAddress(ExperimentView experiment, String address) {
        return experiment.getClassifiers()
                .stream()
                .filter(classifier -> Objects.equal(classifier.getAddress(), address))
                .findAny()
                .orElse(null);
    }

    public void updateExperimentStatus(String address, UUID modelId, ExperimentStatus newStatus) {
        log(log::debug, String.format("Looking for an experiment on a classifier at {%s} and model {%s}",
                address, modelId));
        ExperimentView runningExperiment = queryGateway.query(new RetrieveClassifierModelExperimentQuery(address, modelId),
                instanceOf(ExperimentView.class)).join();
        if (runningExperiment == null) {
            log(log::error, String.format("Found no experiment for address=%s and modelId=%s",
                    address, modelId));
            return;
        }

        ExperimentClassifier runningClassifier = getClassifierByAddress(runningExperiment, address);
        if (runningClassifier == null) {
            log(log::error, String.format("Found no classifier in the experiment for address=%s", address));
            return;
        }

        commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), runningClassifier.getId(), newStatus));
        log(log::info, String.format("The status of the experiment was successfully set to %s", newStatus.getId()));
    }
}
