package org.unibayreuth.gnumaexperiments.dataModel.enums;

public enum ExperimentStatus {
    TRAIN("TRAIN"),
    PAUSE("PAUSE"),
    PAUSING("PAUSING"),
    STOP("STOP"),
    STOPPING("STOPPING"),
    TEST("TEST"),
    FINISH("FINISH"),
    ERROR("ERROR");

    private final String id;

    ExperimentStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
