package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;
import java.util.UUID;

public class ResumeExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;

    public ResumeExperimentCommand(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeExperimentCommand that = (ResumeExperimentCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ResumeExperimentCommand{" +
                "id=" + id +
                '}';
    }
}
