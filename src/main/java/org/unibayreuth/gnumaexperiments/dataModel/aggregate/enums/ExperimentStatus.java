package org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums;

public enum ExperimentStatus {
    TRAIN("TRAIN"),
    PAUSE("PAUSE"),
    STOP("STOP"),
    EVAL("EVAL"),
    FINISH("FINISH");

    private final String id;

    ExperimentStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
