package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;

public class ClassifierDTO {
    private String id;
    private String address;
    private List<HyperParameterDTO> hyperParameters;

    public ClassifierDTO(String id, String address, List<HyperParameterDTO> hyperParameters) {
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

    public List<HyperParameterDTO> getHyperParameters() {
        return hyperParameters;
    }
}
