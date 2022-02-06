package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ResultSourceType;

import java.util.Map;
import java.util.UUID;

public class UpdatedExperimentEvent {
    private UUID id;
    private UUID experimentClassifierId;
    private ExperimentStatus status;
    private Map<String, Double> newResults;
    private Integer currentStep;
    private Integer totalSteps;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;

    public UpdatedExperimentEvent(UUID id, UUID experimentClassifierId, ExperimentStatus status, Map<String, Double> newResults, Integer currentStep, Integer totalSteps, UUID resultSourceId, ResultSourceType resultSourceType) {
        this.id = id;
        this.experimentClassifierId = experimentClassifierId;
        this.status = status;
        this.newResults = newResults;
        this.currentStep = currentStep;
        this.totalSteps = totalSteps;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
    }

    public UUID getId() {
        return id;
    }

    public UUID getExperimentClassifierId() {
        return experimentClassifierId;
    }

    public ExperimentStatus getStatus() {
        return status;
    }

    public Map<String, Double> getNewResults() {
        return newResults;
    }

    public UUID getResultSourceId() {
        return resultSourceId;
    }

    public ResultSourceType getResultSourceType() {
        return resultSourceType;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    @Override
    public String toString() {
        return "UpdatedExperimentEvent{" +
                "id=" + id +
                ", classifierId=" + experimentClassifierId +
                '}';
    }
}
