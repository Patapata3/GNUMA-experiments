package org.unibayreuth.gnumaexperiments.views;

import org.springframework.data.annotation.Id;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;

import java.util.List;

public class ClassifierView {
    @Id
    private String id;
    private String address;
    private List<HyperParameter> hyperParameters;

    public ClassifierView() {
    }

    public ClassifierView(String id, String address, List<HyperParameter> hyperParameters) {
        this.id = id;
        this.address = address;
        this.hyperParameters = hyperParameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<HyperParameter> getHyperParameters() {
        return hyperParameters;
    }

    public void setHyperParameters(List<HyperParameter> hyperParameters) {
        this.hyperParameters = hyperParameters;
    }

    @Override
    public String toString() {
        return "ClassifierView{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
