package org.unibayreuth.gnumaexperiments.exceptions;

public class MissingEntityException extends Exception {
    private final Object entityId;

    public MissingEntityException(Object entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return String.format("No entity found with id: %s", entityId.toString());
    }
}
