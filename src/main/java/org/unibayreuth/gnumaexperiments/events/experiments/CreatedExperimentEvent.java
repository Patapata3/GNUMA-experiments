package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private Date date;
    private List<ExperimentClassifier> classifiers;
    private DataConfig data;
    private String description;

    public CreatedExperimentEvent(UUID id, Date date, List<ExperimentClassifier> classifiers, DataConfig data, String description) {
        this.id = id;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public DataConfig getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(GNUMAConstants.DATE_FORMAT);
        return "CreatedExperimentEvent{" +
                "id=" + id.toString() +
                ", date=" + format.format(date) +
                '}';
    }
}
