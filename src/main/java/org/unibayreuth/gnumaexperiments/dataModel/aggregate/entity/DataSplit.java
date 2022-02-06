package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.List;
import java.util.UUID;

public class DataSplit {
    @EntityId
    private UUID id;
    private List<UUID> trainData;
    private List<UUID> validationData;
    private List<UUID> testData;

    public DataSplit(UUID id, List<UUID> trainData, List<UUID> validationData, List<UUID> testData) {
        this.id = id;
        this.trainData = trainData;
        this.validationData = validationData;
        this.testData = testData;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getTrainData() {
        return trainData;
    }

    public void setTrainData(List<UUID> trainData) {
        this.trainData = trainData;
    }

    public List<UUID> getValidationData() {
        return validationData;
    }

    public void setValidationData(List<UUID> validationData) {
        this.validationData = validationData;
    }

    public List<UUID> getTestData() {
        return testData;
    }

    public void setTestData(List<UUID> testData) {
        this.testData = testData;
    }
}
