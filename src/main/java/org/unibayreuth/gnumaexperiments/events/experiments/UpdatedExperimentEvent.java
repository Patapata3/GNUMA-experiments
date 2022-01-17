package org.unibayreuth.gnumaexperiments.events.experiments;

import java.util.Map;
import java.util.UUID;

public class UpdatedExperimentEvent {
    private UUID id;
    private String status;
    private Map<String, Double> newResults;

    public UpdatedExperimentEvent(UUID id, String status, Map<String, Double> newResults) {
        this.id = id;
        this.status = status;
        this.newResults = newResults;
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
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
