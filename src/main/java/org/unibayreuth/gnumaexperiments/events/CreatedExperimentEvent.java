package org.unibayreuth.gnumaexperiments.events;

import org.unibayreuth.gnumaexperiments.dto.Classifier;

import java.util.Date;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private UUID classifierId;

    public CreatedExperimentEvent(UUID id, UUID classifierId) {
        this.id = id;
        this.classifierId = classifierId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClassifierId() {
        return classifierId;
    }

    @Override
    public String toString() {
        return String.format("CreatedDocumentEvent{id=%s}", id.toString());
    }
}
