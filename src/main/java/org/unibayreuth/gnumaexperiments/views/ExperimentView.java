package org.unibayreuth.gnumaexperiments.views;

import org.springframework.data.annotation.Id;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExperimentView {
    @Id
    private UUID id;
    private Date date;
    private String status;
    private ExperimentClassifier classifier;
    private UUID trainDatasetId;
    private UUID testDatasetId;
    private Map<String, List<Double>> results = new HashMap<>();

    public ExperimentView() {
    }

    public ExperimentView(UUID id, Date date, ExperimentStatus status, ExperimentClassifier classifier, UUID trainDatasetId, UUID testDatasetId) {
        this.id = id;
        this.date = date;
        this.status = status.getId();
        this.classifier = classifier;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExperimentClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(ExperimentClassifier classifier) {
        this.classifier = classifier;
    }

    public Map<String, List<Double>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<Double>> results) {
        this.results = results;
    }

    public ExperimentStatus getStatus() {
        return status == null ? null :
                ExperimentStatus.valueOf(status);
    }

    public void setStatus(ExperimentStatus status) {
        this.status = status.getId();
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public void setTrainDatasetId(UUID trainDatasetId) {
        this.trainDatasetId = trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }

    public void setTestDatasetId(UUID testDatasetId) {
        this.testDatasetId = testDatasetId;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(GNUMAConstants.DATE_FORMAT);
        return "ExperimentView{" +
                "id=" + id +
                ", date=" + format.format(date) +
                '}';
    }
}
