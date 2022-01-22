package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.util.UUID;

public class CreateExperimentCommand {
    private ExperimentStatus status;
    private ExperimentClassifier classifier;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public CreateExperimentCommand(ExperimentStatus status, ExperimentClassifier classifier, UUID trainDatasetId, UUID testDatasetId) {
        this.status = status;
        this.classifier = classifier;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public ExperimentStatus getStatus() {
        return status;
    }

    public ExperimentClassifier getClassifier() {
        return classifier;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }

    @Override
    public String toString() {
        return "CreateExperimentCommand{}";
    }
}
