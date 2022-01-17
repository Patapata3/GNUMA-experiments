package org.unibayreuth.gnumaexperiments.commands.classifiers;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class DeleteClassifierCommand {
    @TargetAggregateIdentifier
    private String id;

    public DeleteClassifierCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteClassifierCommand that = (DeleteClassifierCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DeleteExperimentCommand{" +
                "id='" + id + '\'' +
                '}';
    }
}
