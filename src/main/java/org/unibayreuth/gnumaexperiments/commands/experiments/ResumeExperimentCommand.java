package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ResumeExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private List<ExperimentClassifierDTO> resumedClassifiers;

    public ResumeExperimentCommand(UUID id, List<ExperimentClassifierDTO> resumedClassifiers) {
        this.id = id;
        this.resumedClassifiers = resumedClassifiers;
    }

    public UUID getId() {
        return id;
    }

    public List<ExperimentClassifierDTO> getResumedClassifiers() {
        return resumedClassifiers;
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
