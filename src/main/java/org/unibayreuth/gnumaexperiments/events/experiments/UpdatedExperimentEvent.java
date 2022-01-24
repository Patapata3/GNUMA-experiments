package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ResultSourceType;

import java.util.Map;
import java.util.UUID;

public class UpdatedExperimentEvent {
    private UUID id;
    private ExperimentStatus status;
    private Map<String, Double> newResults;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;

    public UpdatedExperimentEvent(UUID id, ExperimentStatus status, Map<String, Double> newResults, UUID resultSourceId, ResultSourceType resultSourceType) {
        this.id = id;
        this.status = status;
        this.newResults = newResults;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "UpdatedExperimentEvent{" +
                "id=" + id.toString() +
                '}';
    }
}
