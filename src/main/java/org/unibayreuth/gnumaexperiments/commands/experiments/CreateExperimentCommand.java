package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;

public class CreateExperimentCommand {
    private String status;
    private ExperimentClassifier classifier;

    public CreateExperimentCommand(String status, ExperimentClassifier classifier) {
        this.status = status;
        this.classifier = classifier;
    }

    public String getStatus() {
        return status;
    }

    public ExperimentClassifier getClassifier() {
        return classifier;
    }

    @Override
    public String toString() {
        return "CreateExperimentCommand{}";
    }
}
