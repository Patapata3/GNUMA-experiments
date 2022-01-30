package org.unibayreuth.gnumaexperiments.queries.experiments;

import java.util.UUID;

public class RetrieveClassifierModelExperimentQuery {
    private String classifierId;
    private String address;
    private UUID modelId;

    public RetrieveClassifierModelExperimentQuery(String classifierId, String address, UUID modelId) {
        this.classifierId = classifierId;
        this.address = address;
        this.modelId = modelId;
    }

    public String getClassifierId() {
        return classifierId;
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
                "classifierId='" + classifierId + '\'' +
                ", modelId=" + modelId +
                '}';
    }
}
