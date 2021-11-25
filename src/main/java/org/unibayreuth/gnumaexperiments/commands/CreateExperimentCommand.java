package org.unibayreuth.gnumaexperiments.commands;

import java.util.UUID;

public class CreateExperimentCommand {
    private UUID classifierId;

    public CreateExperimentCommand(UUID classifierId) {
        this.classifierId = classifierId;
    }

    public UUID getClassifierId() {
        return classifierId;
    }
}
