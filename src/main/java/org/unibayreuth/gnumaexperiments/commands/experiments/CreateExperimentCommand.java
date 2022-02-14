package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;

import java.util.List;
import java.util.UUID;

public class CreateExperimentCommand {
    private UUID id;
    private List<ExperimentClassifier> classifiers;
    private DataConfig data;
    private String description;

    public CreateExperimentCommand(UUID id, List<ExperimentClassifier> classifiers, DataConfig data, String description) {
        this.id = id;
        this.classifiers = classifiers;
        this.data = data;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public List<ExperimentClassifier> getClassifiers() {
        return classifiers;
    }

    public DataConfig getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CreateExperimentCommand{}";
    }
}
