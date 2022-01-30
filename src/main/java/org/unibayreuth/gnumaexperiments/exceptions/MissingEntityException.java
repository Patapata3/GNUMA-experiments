package org.unibayreuth.gnumaexperiments.exceptions;

import com.google.common.base.Strings;

public class MissingEntityException extends Exception {
    private Object entityId;
    private String entityName;

    public MissingEntityException(Object entityId, String entityName) {
        this.entityId = entityId;
        this.entityName = entityName;
    }

    public MissingEntityException(String message) {
        super(message);
    }

    public MissingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return Strings.isNullOrEmpty(super.getMessage()) ? String.format("No %s found with id: %s", entityName, entityId.toString())
                : super.getMessage();
    }
}
