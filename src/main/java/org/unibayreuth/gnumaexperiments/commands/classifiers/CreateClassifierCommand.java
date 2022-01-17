package org.unibayreuth.gnumaexperiments.commands.classifiers;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;

import java.util.List;
import java.util.Objects;

public class CreateClassifierCommand {
    private String id;
    private String address;
    private List<HyperParameter> hyperParameters;

    public CreateClassifierCommand(String id, String address, List<HyperParameter> hyperParameters) {
        this.id = id;
        this.address = address;
        this.hyperParameters = hyperParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateClassifierCommand that = (CreateClassifierCommand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    public String toString() {
        return "CreateClassifierCommand{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
