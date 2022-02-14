package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;
import org.unibayreuth.gnumaexperiments.dataModel.enums.HyperParameterType;

import java.util.List;

public class HyperParameterDTO {
    private String key;
    private HyperParameterType type;
    private boolean optional;
    @SerializedName(value="default")
    private String defaultValue;
    @SerializedName(value = "value_list", alternate = "valueList")
    private List<String> valueList;
    @SerializedName(value="lower_bound")
    private Double lowerBound;
    @SerializedName(value="upper_bound")
    private Double upperBound;

    public HyperParameterDTO(String key, HyperParameterType type, boolean optional, String defaultValue, List<String> valueList, Double lowerBound, Double upperBound) {
        this.key = key;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.valueList = valueList;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public String getKey() {
        return key;
    }

    public HyperParameterType getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public Double getUpperBound() {
        return upperBound;
    }
}
