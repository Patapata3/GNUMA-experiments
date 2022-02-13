package org.unibayreuth.gnumaexperiments.queries.experiments;

import java.util.UUID;

public class RetrieveClassifierModelExperimentQuery {
    private String address;
    private UUID modelId;

    public RetrieveClassifierModelExperimentQuery(String address, UUID modelId) {
        this.address = address;
        this.modelId = modelId;
    }

    public UUID getModelId() {
        return modelId;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "RetrieveClassifierModelExperimentQuery{" +
                ", modelId=" + modelId +
                '}';
    }
}
