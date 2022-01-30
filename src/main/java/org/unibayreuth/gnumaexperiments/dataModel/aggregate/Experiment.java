package org.unibayreuth.gnumaexperiments.dataModel.aggregate;

import org.axonframework.modelling.command.AggregateMember;
import org.unibayreuth.gnumaexperiments.commands.experiments.CreateExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.DeleteExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.events.experiments.CreatedExperimentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.unibayreuth.gnumaexperiments.events.experiments.DeletedExperimentEvent;

import java.util.*;

@Aggregate
public class Experiment {
    @AggregateIdentifier
    private UUID id;
    private Date date;
    @AggregateMember
    private List<ExperimentClassifier> classifiers;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public Experiment() {}

    @CommandHandler
    public Experiment(CreateExperimentCommand cmd) {
        AggregateLifecycle.apply(new CreatedExperimentEvent(UUID.randomUUID(), new Date(), cmd.getClassifiers(),
                cmd.getTrainDatasetId(), cmd.getTestDatasetId()));
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
        trainDatasetId = event.getTrainDatasetId();
        testDatasetId = event.getTestDatasetId();
    }

    @EventSourcingHandler
    public void handle(DeletedExperimentEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
