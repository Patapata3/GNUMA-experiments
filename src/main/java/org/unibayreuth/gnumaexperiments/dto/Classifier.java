package org.unibayreuth.gnumaexperiments.dto;

import org.unibayreuth.gnumaexperiments.commands.CreateClassifierCommand;
import org.unibayreuth.gnumaexperiments.events.CreatedClassifierEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class Classifier {
    @AggregateIdentifier
    private UUID id;
    private String name;
    private String address;

    public Classifier() {}

    @CommandHandler
    public Classifier(CreateClassifierCommand cmd) {
        AggregateLifecycle.apply(new CreatedClassifierEvent(UUID.randomUUID(), cmd.getName(), cmd.getAddress()));
    }

    @EventSourcingHandler
    public void handle(CreatedClassifierEvent event) {
        id = event.getId();
        address = event.getAddress();
        name = event.getName();
    }
}
