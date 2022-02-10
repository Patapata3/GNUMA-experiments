package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.List;
import java.util.UUID;

public class DataSplit {
    @EntityId
    private UUID id;
    private List<String> trainData;
    private List<String> validationData;
    private List<String> testData;

    public DataSplit(UUID id, List<String> trainData, List<String> validationData, List<String> testData) {
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

    public List<String> getTrainData() {
        return trainData;
    }

    public void setTrainData(List<String> trainData) {
        this.trainData = trainData;
    }

    public List<String> getValidationData() {
        return validationData;
    }

    public void setValidationData(List<String> validationData) {
        this.validationData = validationData;
    }

    public List<String> getTestData() {
        return testData;
    }

    public void setTestData(List<String> testData) {
        this.testData = testData;
    }
}
