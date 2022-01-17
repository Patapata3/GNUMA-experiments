package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

public class ExperimentClassifier {
    @EntityId
    private UUID id;
    private String remoteId;
    private String address;
    private Model model;

    public ExperimentClassifier(UUID id, String remoteId, String address, Model model) {
        this.id = id;
        this.remoteId = remoteId;
        this.address = address;
        this.model = model;
    }

    public UUID getId() {
        return id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public String getAddress() {
        return address;
    }

    public Model getModel() {
        return model;
    }
}
