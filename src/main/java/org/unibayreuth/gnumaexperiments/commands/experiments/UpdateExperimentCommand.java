package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ResultSourceType;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UpdateExperimentCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private ExperimentStatus status;
    private Map<String, Double> newResults;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;

    public UpdateExperimentCommand(UUID id, ExperimentStatus status, Map<String, Double> newResults) {
        this.id = id;
        this.status = status;
        this.newResults = newResults;
    }

    public UpdateExperimentCommand(UUID id, ExperimentStatus status, Map<String, Double> newResults, UUID resultSourceId, ResultSourceType resultSourceType) {
        this.id = id;
        this.status = status;
        this.newResults = newResults;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
    }

    public UUID getId() {
        return id;
    }

    public ExperimentStatus getStatus() {
        return status;
    }

    public Map<String, Double> getNewResults() {
        return newResults;
    }

    public UUID getResultSourceId() {
        return resultSourceId;
    }

    public ResultSourceType getResultSourceType() {
        return resultSourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateExperimentCommand that = (UpdateExperimentCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UpdateExperimentCommand{" +
                "id=" + id.toString() +
                '}';
    }
}
