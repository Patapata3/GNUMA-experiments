package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StopExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private List<ExperimentClassifierDTO> stoppedClassifiers;

    public StopExperimentCommand(UUID id, List<ExperimentClassifierDTO> stoppedClassifiers) {
        this.id = id;
        this.stoppedClassifiers = stoppedClassifiers;
    }

    public UUID getId() {
        return id;
    }

    public List<ExperimentClassifierDTO> getStoppedClassifiers() {
        return stoppedClassifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopExperimentCommand that = (StopExperimentCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StopExperimentCommand{" +
                "id=" + id +
                '}';
    }
}
