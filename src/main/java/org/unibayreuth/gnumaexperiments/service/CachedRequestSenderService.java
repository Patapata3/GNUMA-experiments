package org.unibayreuth.gnumaexperiments.service;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface CachedRequestSenderService {
    HttpResponse<String> sendRequest(String uri) throws IOException, InterruptedException;
    HttpResponse<String> sendRequest(String uri, String... headers) throws IOException, InterruptedException;
}
