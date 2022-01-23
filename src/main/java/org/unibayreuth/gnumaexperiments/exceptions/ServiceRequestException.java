package org.unibayreuth.gnumaexperiments.exceptions;

import com.google.common.base.Strings;

import javax.annotation.Nullable;

public class ServiceRequestException extends Exception {
    private final int responseCode;
    @Nullable
    private final String responseBody;
    private final String uri;

    public ServiceRequestException(int responseCode, @Nullable String responseBody, String uri) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.uri = uri;
    }

    @Override
    public String getMessage() {
        return String.format("Request to %s was unsuccessful, status: %d %s", uri, responseCode,
                Strings.isNullOrEmpty(responseBody) ? String.format("reason: %s", responseBody) : "");
    }
}
