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
    private String status;
    private ExperimentClassifier classifier;
    private Map<String, List<Double>> results = new HashMap<>();

    public ExperimentView() {
    }

    public ExperimentView(UUID id, Date date, String status, ExperimentClassifier classifier) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.classifier = classifier;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
