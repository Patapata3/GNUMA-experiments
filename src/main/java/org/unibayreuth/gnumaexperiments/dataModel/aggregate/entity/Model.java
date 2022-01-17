package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.Map;
import java.util.UUID;

public class Model {
    @EntityId
    private UUID id;
    private UUID remoteId;
    private Map<String, String> hyperParameterValues;

    public Model(UUID id, UUID remoteId, Map<String, String> hyperParameterValues) {
        this.id = id;
        this.remoteId = remoteId;
        this.hyperParameterValues = hyperParameterValues;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRemoteId() {
        return remoteId;
    }

    public Map<String, String> getHyperParameterValues() {
        return hyperParameterValues;
    }
}
