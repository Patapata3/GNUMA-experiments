package org.unibayreuth.gnumaexperiments.exceptions;

public class MissingClassifierException extends Exception {
    private String classifierId;

    public MissingClassifierException(String classifierId) {
        super();
        this.classifierId = classifierId;
    }

    @Override
    public String getMessage() {
        return String.format("No classifier found with id: %s", classifierId);
    }
}
