package org.unibayreuth.gnumaexperiments.dataModel.enums;

public enum HyperParameterType {
    STRING("STRING"),
    INTEGER("INTEGER"),
    BOOLEAN("BOOLEAN"),
    DOUBLE("DOUBLE");

    private final String id;

    HyperParameterType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
