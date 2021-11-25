package org.unibayreuth.gnumaexperiments.dto;

import org.unibayreuth.gnumaexperiments.commands.CreateExperimentCommand;
import org.unibayreuth.gnumaexperiments.events.CreatedExperimentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.UUID;

@Aggregate
public class Experiment {
    @AggregateIdentifier
    private UUID id;
    private Date date;

    public Experiment() {}

    @CommandHandler
    public Experiment(CreateExperimentCommand cmd) {
        AggregateLifecycle.apply(new CreatedExperimentEvent(UUID.randomUUID(), cmd.getDate()));
    }

    @EventSourcingHandler
    public void handle(CreatedExperimentEvent event) {
        id = event.getId();
        date = event.getDate();
    }

}
