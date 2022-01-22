package org.unibayreuth.gnumaexperiments.dto;

import java.util.Map;

public class ExperimentClassifierDTO {
    private String id;
    private String address;
    private Map<String, String> hyperParameterValues;

    public ExperimentClassifierDTO(String id, String address, Map<String, String> hyperParameterValues) {
        this.id = id;
        this.address = address;
        this.hyperParameterValues = hyperParameterValues;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Map<String, String> getHyperParameterValues() {
        return hyperParameterValues;
    }
}
