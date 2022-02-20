package org.unibayreuth.gnumaexperiments.dataModel.aggregate;

import org.axonframework.modelling.command.AggregateMember;
import org.unibayreuth.gnumaexperiments.commands.classifiers.CreateClassifierCommand;
import org.unibayreuth.gnumaexperiments.commands.classifiers.DeleteClassifierCommand;
import org.unibayreuth.gnumaexperiments.commands.classifiers.UpdateClassifierCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.events.classifiers.CreatedClassifierEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.unibayreuth.gnumaexperiments.events.classifiers.DeletedClassifierEvent;
import org.unibayreuth.gnumaexperiments.events.classifiers.UpdatedClassifierEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Aggregate(snapshotTriggerDefinition = "experimentSnapshotTriggerDefinition")
public class Classifier {
    private String id;
    @AggregateIdentifier
    private String address;
    private Date lastUpdate;
    @AggregateMember
    private List<HyperParameter> hyperParameters;

    public Classifier() {}

    @CommandHandler
    public Classifier(CreateClassifierCommand cmd) {
        AggregateLifecycle.apply(new CreatedClassifierEvent(cmd.getId(), cmd.getAddress(), cmd.getHyperParameters()));
    }

    @CommandHandler
    public void handle(DeleteClassifierCommand cmd) {
        AggregateLifecycle.apply(new DeletedClassifierEvent(cmd.getId()));
    }

    @CommandHandler
    public void handle(UpdateClassifierCommand cmd)  {
        AggregateLifecycle.apply(new UpdatedClassifierEvent(cmd.getId(), cmd.getAddress(), cmd.getHyperParameters()));
    }

    @EventSourcingHandler
    public void handle(CreatedClassifierEvent event) {
        id = event.getId();
        address = event.getAddress();
        lastUpdate = new Date();
        this.hyperParameters = Objects.isNull(event.getHyperParameters()) ? new ArrayList<>() : event.getHyperParameters();
    }

    @EventSourcingHandler
    public void handle(DeletedClassifierEvent event) {
        markDeleted();
    }

    @EventSourcingHandler
    public void handle(UpdatedClassifierEvent event) {
        id = event.getId();
        hyperParameters = event.getHyperParameters();
        lastUpdate = new Date();
    }
}
