package org.unibayreuth.gnumaexperiments.queries.experiments;

import java.util.UUID;

public class RetrieveIdExperimentQuery {
    private UUID id;

    public RetrieveIdExperimentQuery(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
