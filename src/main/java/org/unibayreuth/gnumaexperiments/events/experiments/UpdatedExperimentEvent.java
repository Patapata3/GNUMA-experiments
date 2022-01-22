package org.unibayreuth.gnumaexperiments.events.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;

import java.util.Map;
import java.util.UUID;

public class UpdatedExperimentEvent {
    private UUID id;
    private ExperimentStatus status;
    private Map<String, Double> newResults;

    public UpdatedExperimentEvent(UUID id, ExperimentStatus status, Map<String, Double> newResults) {
        this.id = id;
        this.status = status;
        this.newResults = newResults;
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

    @Override
    public String toString() {
        return "UpdatedExperimentEvent{" +
                "id=" + id +
                '}';
    }
}
