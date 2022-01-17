package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;

public class HyperParameterDTO {
    private String key;
    private String type;
    private boolean optional;
    private String defaultValue;
    private List<String> valueList;

    public HyperParameterDTO(String key, String type, boolean optional, String defaultValue, List<String> valueList) {
        this.key = key;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.valueList = valueList;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
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
