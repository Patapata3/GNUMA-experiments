package org.unibayreuth.gnumaexperiments.exceptions;

public class MissingEntityException extends Exception {
    private final Object entityId;
    private final String entityName;

    public MissingEntityException(Object entityId, String entityName) {
        this.entityId = entityId;
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return String.format("No %s found with id: %s", entityName, entityId.toString());
    }
}
