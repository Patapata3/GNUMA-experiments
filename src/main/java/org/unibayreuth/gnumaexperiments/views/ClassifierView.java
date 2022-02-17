package org.unibayreuth.gnumaexperiments.views;

import org.springframework.data.annotation.Id;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;

import java.util.Date;
import java.util.List;

public class ClassifierView {
    private String id;
    @Id
    private String address;
    private List<HyperParameter> hyperParameters;
    private Date lastUpdate;

    public ClassifierView() {
    }

    public ClassifierView(String id, String address, List<HyperParameter> hyperParameters, Date lastUpdate) {
        this.id = id;
        this.address = address;
        this.hyperParameters = hyperParameters;
        this.lastUpdate = lastUpdate;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ClassifierView{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
