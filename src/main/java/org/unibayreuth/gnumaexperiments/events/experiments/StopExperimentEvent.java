package org.unibayreuth.gnumaexperiments.events.experiments;

import java.util.UUID;

public class StopExperimentEvent {
    private UUID id;

    public StopExperimentEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StopExperimentEvent{" +
                "id=" + id +
                '}';
    }
}
