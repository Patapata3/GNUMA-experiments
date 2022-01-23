package org.unibayreuth.gnumaexperiments.commands.classifiers;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;

import java.util.List;
import java.util.Objects;

public class UpdateClassifierCommand {
    @TargetAggregateIdentifier
    private String id;
    private String address;
    private List<HyperParameter> hyperParameters;

    public UpdateClassifierCommand(String id, String address, List<HyperParameter> hyperParameters) {
        this.id = id;
        this.address = address;
        this.hyperParameters = hyperParameters;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public List<HyperParameter> getHyperParameters() {
        return hyperParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateClassifierCommand that = (UpdateClassifierCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UpdateClassifierCommand{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
