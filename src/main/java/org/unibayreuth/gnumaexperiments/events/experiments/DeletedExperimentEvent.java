package org.unibayreuth.gnumaexperiments.events.experiments;

import java.util.UUID;

public class DeletedExperimentEvent {
    private final UUID id;

    public DeletedExperimentEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DeletedExperimentEvent{" +
                "id=" + id.toString() +
                '}';
    }
}

