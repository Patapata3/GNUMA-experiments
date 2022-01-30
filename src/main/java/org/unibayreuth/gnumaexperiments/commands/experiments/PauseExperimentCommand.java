package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PauseExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private List<ExperimentClassifierDTO> pausedClassifiers;

    public PauseExperimentCommand(UUID id, List<ExperimentClassifierDTO> pausedClassifiers) {
        this.id = id;
        this.pausedClassifiers = pausedClassifiers;
    }

    public UUID getId() {
        return id;
    }

    public List<ExperimentClassifierDTO> getPausedClassifiers() {
        return pausedClassifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PauseExperimentCommand that = (PauseExperimentCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PauseExperimentCommand{" +
                "id=" + id +
                '}';
    }
}
