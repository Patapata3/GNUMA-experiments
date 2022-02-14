package org.unibayreuth.gnumaexperiments.dataModel.enums;

public enum ResultSourceType {
    DATASET("DATASET"),
    DOCUMENT("DOCUMENT");

    private final String id;

    ResultSourceType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
