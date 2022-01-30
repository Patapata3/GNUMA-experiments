package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.util.List;
import java.util.UUID;

public class CreateExperimentCommand {
    private List<ExperimentClassifier> classifiers;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public CreateExperimentCommand(List<ExperimentClassifier> classifiers, UUID trainDatasetId, UUID testDatasetId) {
        this.classifiers = classifiers;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public List<ExperimentClassifier> getClassifiers() {
        return classifiers;
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
