package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private Date date;
    private List<ExperimentClassifier> classifiers;
    private UUID trainDatasetId;
    private UUID testDatasetId;

    public CreatedExperimentEvent(UUID id, Date date, List<ExperimentClassifier> classifiers, UUID trainDatasetId, UUID testDatasetId) {
        this.id = id;
        this.date = date;
        this.classifiers = classifiers;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
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
                "id=" + id.toString() +
                ", date=" + format.format(date) +
                '}';
    }
}
