package org.unibayreuth.gnumaexperiments.dataModel.aggregate;

import org.axonframework.modelling.command.AggregateMember;
import org.unibayreuth.gnumaexperiments.commands.experiments.CreateExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.DeleteExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.events.experiments.CreatedExperimentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.unibayreuth.gnumaexperiments.events.experiments.DeletedExperimentEvent;

import java.util.*;

@Aggregate(snapshotTriggerDefinition = "experimentSnapshotTriggerDefinition")
public class Experiment {
    @AggregateIdentifier
    private UUID id;
    private Date date;
    private String description;
    @AggregateMember
    private List<ExperimentClassifier> classifiers;
    @AggregateMember
    private DataConfig data;

    public Experiment() {}

    @CommandHandler
    public Experiment(CreateExperimentCommand cmd) {
        AggregateLifecycle.apply(new CreatedExperimentEvent(cmd.getId(), new Date(), cmd.getClassifiers(),
                cmd.getData(), cmd.getDescription()));
    }

    @CommandHandler
    public void handle(DeleteExperimentCommand cmd) {
        AggregateLifecycle.apply(new DeletedExperimentEvent(cmd.getId()));
    }

    @EventSourcingHandler
    public void handle(CreatedExperimentEvent event) {
        id = event.getId();
        date = event.getDate();
        classifiers = event.getClassifiers();
        data = event.getData();
        description = event.getDescription();
    }

    @EventSourcingHandler
    public void handle(DeletedExperimentEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
