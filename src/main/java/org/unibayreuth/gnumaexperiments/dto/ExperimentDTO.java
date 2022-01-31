package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.UUID;

public class ExperimentDTO {
    private List<ExperimentClassifierDTO> classifiers;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public ExperimentDTO(List<ExperimentClassifierDTO> classifiers, UUID trainDatasetId, UUID testDatasetId) {
        this.classifiers = classifiers;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public List<ExperimentClassifierDTO> getClassifiers() {
        return classifiers;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }
}
