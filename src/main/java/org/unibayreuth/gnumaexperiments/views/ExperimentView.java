package org.unibayreuth.gnumaexperiments.views;

import org.springframework.data.annotation.Id;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExperimentView {
    @Id
    private UUID id;
    private Date date;
    private List<ExperimentClassifier> classifiers;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public ExperimentView() {
    }

    public ExperimentView(UUID id, Date date, List<ExperimentClassifier> classifiers, UUID trainDatasetId, UUID testDatasetId) {
        this.id = id;
        this.date = date;
        this.classifiers = classifiers;
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

    public List<ExperimentClassifier> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<ExperimentClassifier> classifiers) {
        this.classifiers = classifiers;
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
                "id=" + id.toString() +
                ", date=" + format.format(date) +
                '}';
    }
}
