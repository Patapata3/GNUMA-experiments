package org.unibayreuth.gnumaexperiments.dto;

import java.util.Map;
import java.util.UUID;

public class TrainRequestDTO {
    private UUID datasetId;
    private Map<String, String> hyperParameters;

    public TrainRequestDTO(UUID datasetId, Map<String, String> hyperParameters) {
        this.datasetId = datasetId;
        this.hyperParameters = hyperParameters;
    }

    public UUID getDatasetId() {
        return datasetId;
    }

    public Map<String, String> getHyperParameters() {
        return hyperParameters;
    }
}
