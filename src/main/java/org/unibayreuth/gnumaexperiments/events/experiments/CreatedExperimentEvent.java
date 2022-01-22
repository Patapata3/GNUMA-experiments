package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private Date date;
    private ExperimentStatus status;
    private ExperimentClassifier classifier;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public CreatedExperimentEvent(UUID id, Date date, ExperimentStatus status, ExperimentClassifier classifier, UUID trainDatasetId, UUID testDatasetId) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.classifier = classifier;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
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

    public ExperimentStatus getStatus() {
        return status;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
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
