package org.unibayreuth.gnumaexperiments.handlers.exceptionhandling;

import org.axonframework.commandhandling.CommandExecutionException;

/**
 * Error object containing information on the exception before it was wrapped into {@link CommandExecutionException}
 */
public class ExperimentError {
    private final String name;
    private final String message;
    private final ExperimentErrorCode errorCode;

    public ExperimentError(String name, String message, ExperimentErrorCode errorCode) {
        this.name = name;
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public ExperimentErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ExperimentError{" +
                "errorCode=" + errorCode.toString() +
                '}';
    }
}
