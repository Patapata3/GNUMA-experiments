package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassifierDTO {
    @SerializedName(value="classifier_name")
    private String classifierName;
    private String address;
    @SerializedName(value = "hyper_parameters")
    private List<HyperParameterDTO> hyperParameters;

    public ClassifierDTO(String classifierName, String address, List<HyperParameterDTO> hyperParameters) {
        this.classifierName = classifierName;
        this.address = address;
        this.hyperParameters = hyperParameters;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public String getAddress() {
        return address;
    }

    public List<HyperParameterDTO> getHyperParameters() {
        return hyperParameters;
    }
}
