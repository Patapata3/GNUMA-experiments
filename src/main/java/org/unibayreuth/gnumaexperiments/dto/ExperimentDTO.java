package org.unibayreuth.gnumaexperiments.dto;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.DataConfig;

import java.util.List;
import java.util.UUID;

public class ExperimentDTO {
    private List<ExperimentClassifierDTO> classifiers;
    private DataConfig data;
    private String description;

    public ExperimentDTO(List<ExperimentClassifierDTO> classifiers, UUID trainDatasetId, UUID testDatasetId, DataConfig data, String description) {
        this.classifiers = classifiers;
        this.data = data;
        this.description = description;
    }

    public List<ExperimentClassifierDTO> getClassifiers() {
        return classifiers;
    }

    public DataConfig getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }
}
