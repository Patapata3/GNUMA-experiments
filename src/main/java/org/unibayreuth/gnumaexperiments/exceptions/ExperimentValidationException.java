package org.unibayreuth.gnumaexperiments.exceptions;

public class ExperimentValidationException extends Exception{

    public ExperimentValidationException(String message) {
        super(message);
    }

    public ExperimentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
