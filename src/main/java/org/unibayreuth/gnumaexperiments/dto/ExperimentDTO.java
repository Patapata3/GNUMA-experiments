package org.unibayreuth.gnumaexperiments.dto;

import java.util.UUID;

public class ExperimentDTO {
    private ExperimentClassifierDTO classifier;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public ExperimentDTO(ExperimentClassifierDTO classifier, UUID trainDatasetId, UUID testDatasetId) {
        this.classifier = classifier;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public ExperimentClassifierDTO getClassifier() {
        return classifier;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }
}
