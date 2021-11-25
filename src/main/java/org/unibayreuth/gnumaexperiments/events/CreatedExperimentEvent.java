package org.unibayreuth.gnumaexperiments.events;

import java.util.Date;
import java.util.UUID;

public class CreatedExperimentEvent {
    private final UUID id;
    private Date date;

    public CreatedExperimentEvent(UUID id, Date date) {
        this.id = id;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("CreatedDocumentEvent{id=%s}", id.toString());
    }
}
