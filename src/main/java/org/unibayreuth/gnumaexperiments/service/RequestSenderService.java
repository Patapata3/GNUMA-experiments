package org.unibayreuth.gnumaexperiments.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface RequestSenderService {
    HttpResponse<String> sendGetRequest(String uri) throws IOException, InterruptedException;
    HttpResponse<String> sendGetRequest(String uri, String... headers) throws IOException, InterruptedException;
}
