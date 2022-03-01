package org.unibayreuth.gnumaexperiments.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Set;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

/**
 * Request sending service
 */
@Service(RequestSenderService.NAME)
@Primary
public class RequestSenderServiceBean implements RequestSenderService {
    private final Logger log = LoggerFactory.getLogger(RequestSenderServiceBean.class);

    private final Set<Integer> SUCCESS_CODES = Sets.newHashSet(HttpResponseStatus.OK.code(), HttpResponseStatus.ACCEPTED.code());

    /**
     * Send a get request
     * @param uri - address of a resource being requested
     * @param headers - request headers
     * @return - response from the resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     * @throws ServiceRequestException - resource returned non-ok response
     */
    @Override
    public HttpResponse<String> sendGetRequest(String uri, String... headers) throws IOException, InterruptedException, ServiceRequestException {
        log(log::info, String.format("Sending GET request to %s with headers:\n%s", uri, Arrays.toString(headers)));
        Preconditions.checkNotNull(uri, "URI cannot be null");
        Preconditions.checkNotNull(headers, "headers list cannot be null");
        Preconditions.checkArgument(headers.length % 2 == 0,
                "Headers list should contain even number of entries");

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .GET();
        return sendRequestFromBuilder(requestBuilder, headers);
    }

    /**
     * Send a post request
     * @param uri - address of the resource request is sent to
     * @param body - request body
     * @param headers - request headers
     * @return - response from the resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     * @throws ServiceRequestException - resource returned non-ok response
     */
    @Override
    public HttpResponse<String> sendPostRequest(String uri, @Nullable String body, String... headers) throws IOException, InterruptedException, ServiceRequestException {
        log(log::info, String.format("Sending POST request to %s with headers:\n%s\n and body:\n%s", uri, Arrays.toString(headers), body));
        Preconditions.checkNotNull(uri, "URI cannot be null");
        Preconditions.checkNotNull(headers, "headers list cannot be null");
        Preconditions.checkArgument(headers.length % 2 == 0,
                "Headers list should contain even number of entries");

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .POST(Strings.isNullOrEmpty(body) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        return sendRequestFromBuilder(requestBuilder, headers);
    }

    /**
     * Send put request
     * @param uri - address of the resource request is sent to
     * @param body - request body
     * @param headers - request headers
     * @return - response from the resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     * @throws ServiceRequestException - resource returned non-ok response
     */
    @Override
    public HttpResponse<String> sendPutRequest(String uri, @Nullable String body, String... headers) throws InterruptedException, ServiceRequestException, IOException {
        log(log::info, String.format("Sending PUT request to %s with headers:\n%s\n and body:\n%s", uri, Arrays.toString(headers), body));
        Preconditions.checkNotNull(uri, "URI cannot be null");
        Preconditions.checkNotNull(headers, "headers list cannot be null");
        Preconditions.checkArgument(headers.length % 2 == 0,
                "Headers list should contain even number of entries");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .PUT(Strings.isNullOrEmpty(body) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        return sendRequestFromBuilder(requestBuilder, headers);
    }

    /**
     * Send a delete request
     * @param uri - address of a resource being requested
     * @param headers - request headers
     * @return - response from the resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     * @throws ServiceRequestException - resource returned non-ok response
     */
    @Override
    public HttpResponse<String> sendDeleteRequest(String uri, String... headers) throws InterruptedException, ServiceRequestException, IOException {
        log(log::info, String.format("Sending DELETE request to %s with headers:\n%s\n", uri, Arrays.toString(headers)));
        Preconditions.checkNotNull(uri, "URI cannot be null");
        Preconditions.checkNotNull(headers, "headers list cannot be null");
        Preconditions.checkArgument(headers.length % 2 == 0,
                "Headers list should contain even number of entries");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .DELETE();
        return sendRequestFromBuilder(requestBuilder, headers);
    }

    private HttpResponse<String> sendRequestFromBuilder(HttpRequest.Builder requestBuilder, String... headers) throws IOException, InterruptedException, ServiceRequestException {
        if (headers.length > 0) {
            requestBuilder.headers(headers);
        }
        HttpRequest request = requestBuilder.build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (!SUCCESS_CODES.contains(response.statusCode())) {
            log(log::error, String.format("Response returned with status: %s", response.statusCode()));
            throw new ServiceRequestException(response.statusCode(), response.body(), request.uri().toString());
        }
        log(log::info, "Request executed successfully");
        return response;
    }
}
