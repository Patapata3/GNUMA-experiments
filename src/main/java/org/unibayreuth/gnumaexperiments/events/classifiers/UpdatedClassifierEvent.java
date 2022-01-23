package org.unibayreuth.gnumaexperiments.events.classifiers;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;

import java.util.List;

public class UpdatedClassifierEvent {
    private final String id;
    private String address;
    private List<HyperParameter> hyperParameters;

    public UpdatedClassifierEvent(String id, String address, List<HyperParameter> hyperParameters) {
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
    public String toString() {
        return "UpdatedClassifierEvent{" +
                "id='" + id + '\'' +
                '}';
    }
}
