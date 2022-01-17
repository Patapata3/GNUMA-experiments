package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;
import java.util.UUID;

public class DeleteExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;

    public DeleteExperimentCommand(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteExperimentCommand that = (DeleteExperimentCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DeleteExperimentCommand{" +
                "id=" + id +
                '}';
    }
}
