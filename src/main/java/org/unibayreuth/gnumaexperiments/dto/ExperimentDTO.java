package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class ExperimentDTO {
    private List<ExperimentClassifierDTO> classifier;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public ExperimentDTO(List<ExperimentClassifierDTO> classifier, UUID trainDatasetId, UUID testDatasetId) {
        this.classifier = classifier;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public List<ExperimentClassifierDTO> getClassifier() {
        return classifier;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }
}
