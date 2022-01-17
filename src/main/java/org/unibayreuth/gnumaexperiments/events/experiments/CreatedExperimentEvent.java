package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private Date date;
    private String status;
    private ExperimentClassifier classifier;

    public CreatedExperimentEvent(UUID id, Date date, String status, ExperimentClassifier classifier) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.classifier = classifier;
    }

    public UUID getId() {
        return id;
    }

    public ExperimentClassifier getClassifier() {
        return classifier;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat(GNUMAConstants.DATE_FORMAT);
        return "CreatedExperimentEvent{" +
                "id=" + id +
                ", date=" + format.format(date) +
                '}';
    }
}
