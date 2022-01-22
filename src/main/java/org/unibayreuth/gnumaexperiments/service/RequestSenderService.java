package org.unibayreuth.gnumaexperiments.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface RequestSenderService {
    String NAME = "gnuma_RequestSenderService";
    HttpResponse<String> sendGetRequest(String uri, String... headers) throws IOException, InterruptedException;
    HttpResponse<String> sendPostRequest(String uri, String body, String... headers) throws IOException, InterruptedException;
}
