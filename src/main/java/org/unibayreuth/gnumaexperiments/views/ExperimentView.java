package org.unibayreuth.gnumaexperiments.views;

import org.springframework.data.annotation.Id;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExperimentView {
    @Id
    private UUID id;
    private Date date;
    private List<ExperimentClassifier> classifiers;
    private DataConfig data;
    private String description;

    public ExperimentView() {
    }

    public ExperimentView(UUID id, Date date, List<ExperimentClassifier> classifiers, DataConfig data, String description) {
        this.id = id;
        this.date = date;
        this.classifiers = classifiers;
        this.data = data;
        this.description = description;
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

    public DataConfig getData() {
        return data;
    }

    public void setData(DataConfig data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
