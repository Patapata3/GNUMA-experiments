package org.unibayreuth.gnumaexperiments.service;

import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Request sending service
 */
@Service("senderService")
@Primary
public class RequestSenderServiceBean implements RequestSenderService {

    /**
     * Send a get request
     * @param uri - address of a resource being requested
     * @param headers - request headers
     * @return - response from the resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     */
    @Override
    public HttpResponse<String> sendGetRequest(String uri, String... headers) throws IOException, InterruptedException {
        Preconditions.checkNotNull(uri, "URI cannot be null");
        Preconditions.checkNotNull(headers, "headers list cannot be null");
        Preconditions.checkArgument(headers.length % 2 == 0,
                "Headers list should contain even number of entries");


        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .GET();
        if (headers.length > 0) {
            requestBuilder.headers(headers);
        }
        HttpRequest request = requestBuilder.build();

        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
