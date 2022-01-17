package org.unibayreuth.gnumaexperiments.dataModel.aggregate;

import org.axonframework.modelling.command.AggregateMember;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.commands.experiments.CreateExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.DeleteExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.StopExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.UpdateExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.events.experiments.CreatedExperimentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.unibayreuth.gnumaexperiments.events.experiments.DeletedExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.StopExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.UpdatedExperimentEvent;

import java.util.*;

@Aggregate
public class Experiment {
    @AggregateIdentifier
    private UUID id;
    private Date date;
    private String status;
    @AggregateMember
    private ExperimentClassifier classifier;
    private Map<String, List<Double>> results;

    public Experiment() {}

    @CommandHandler
    public Experiment(CreateExperimentCommand cmd) {
        AggregateLifecycle.apply(new CreatedExperimentEvent(UUID.randomUUID(), new Date(), cmd.getStatus(), cmd.getClassifier()));
    }

    @CommandHandler
    public void handle(DeleteExperimentCommand cmd) {
        AggregateLifecycle.apply(new DeletedExperimentEvent(cmd.getId()));
    }

    @CommandHandler
    public void handle(StopExperimentCommand cmd) {
        AggregateLifecycle.apply(new StopExperimentEvent(cmd.getId()));
    }

    @CommandHandler
    public void handle(UpdateExperimentCommand cmd) {
        AggregateLifecycle.apply(new UpdatedExperimentEvent(cmd.getId(), cmd.getStatus(), cmd.getNewResults()));
    }

    @EventSourcingHandler
    public void handle(CreatedExperimentEvent event) {
        id = event.getId();
        date = event.getDate();
        status = event.getStatus();
        classifier = event.getClassifier();
        results = new HashMap<>();
    }

    @EventSourcingHandler
    public void handle(DeletedExperimentEvent event) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    public void handle(StopExperimentEvent event) {
        status = GNUMAConstants.STOP_STATUS;
    }

    @EventSourcingHandler
    public void handle(UpdatedExperimentEvent event) {
        status = event.getStatus();
        if (!Objects.isNull(event.getNewResults())) {
            event.getNewResults().forEach((key, value) -> {
                if (!results.containsKey(key)) {
                    results.put(key, new ArrayList<>());
                }
                results.get(key).add(value);
            });
        }
    }
}
