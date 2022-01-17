package org.unibayreuth.gnumaexperiments.queries.experiments;

import java.util.UUID;

public class RetrieveClassifierModelExperimentQuery {
    private String classifierId;
    private UUID modelId;

    public RetrieveClassifierModelExperimentQuery(String classifierId, UUID modelId) {
        this.classifierId = classifierId;
        this.modelId = modelId;
    }

    public String getClassifierId() {
        return classifierId;
    }

    public UUID getModelId() {
        return modelId;
    }

    @Override
    public String toString() {
        return "RetrieveClassifierModelExperimentQuery{" +
                "classifierId='" + classifierId + '\'' +
                ", modelId=" + modelId +
                '}';
    }
}
