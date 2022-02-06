package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.UUID;

public class DataConfig {
    @EntityId
    private UUID id;
    private UUID datasetId;
    private Double testSplit;
    private Double validationSplit;
    private Integer seed;
    private DataSplit dataSplit;

    public DataConfig(UUID id, UUID datasetId, Double testSplit, Double validationSplit, Integer seed, DataSplit dataSplit) {
        this.id = id;
        this.datasetId = datasetId;
        this.testSplit = testSplit;
        this.validationSplit = validationSplit;
        this.seed = seed;
        this.dataSplit = dataSplit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(UUID datasetId) {
        this.datasetId = datasetId;
    }

    public Double getTestSplit() {
        return testSplit;
    }

    public void setTestSplit(Double testSplit) {
        this.testSplit = testSplit;
    }

    public Double getValidationSplit() {
        return validationSplit;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setValidationSplit(Double validationSplit) {
        this.validationSplit = validationSplit;
    }

    public DataSplit getDataSplit() {
        return dataSplit;
    }

    public void setDataSplit(DataSplit dataSplit) {
        this.dataSplit = dataSplit;
    }
}
