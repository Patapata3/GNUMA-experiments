package org.unibayreuth.gnumaexperiments.dto;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.HyperParameterType;

import java.util.List;

public class HyperParameterDTO {
    private String key;
    private HyperParameterType type;
    private boolean optional;
    private String defaultValue;
    private List<String> valueList;

    public HyperParameterDTO(String key, HyperParameterType type, boolean optional, String defaultValue, List<String> valueList) {
        this.key = key;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.valueList = valueList;
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
}
