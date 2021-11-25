package org.unibayreuth.gnumaexperiments.service;

import org.springframework.stereotype.Service;
import org.unibayreuth.gnumaexperiments.cache.RequestCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * Service, responsible for sending requests, responses of which are stored in cache
 */
@Service
public class CachedRequestSenderServiceBean implements CachedRequestSenderService {
    private final RequestCache requestCache;
    private final RequestSenderService requestSenderService;

    @Autowired
    public CachedRequestSenderServiceBean(RequestCache requestCache, RequestSenderService requestSenderService) {
        this.requestCache = requestCache;
        this.requestSenderService = requestSenderService;
    }

    /**
     * Sending a cached request only with a default header
     * @param uri - address of the resource to which request is sent
     * @return - response, either from cache, or from the requested resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     */
    @Override
    public HttpResponse<String> sendRequest(String uri) throws IOException, InterruptedException {
        return sendRequest(uri, "content-type", "application/json");
    }

    /**
     * Sending a cached request
     * @param uri - address of the resource to which request is sent
     * @param headers - headers
     * @return - response, either from cache, or from the requested resource
     * @throws IOException - malformed or incorrect uri
     * @throws InterruptedException - connection to the resource was interrupted
     */
    @Override
    public HttpResponse<String> sendRequest(String uri, String... headers) throws IOException, InterruptedException {
        if (requestCache.contains(uri)) {
            return requestCache.get(uri);
        }

        HttpResponse<String> response =  requestSenderService.sendGetRequest(uri, headers);
        requestCache.add(uri, response);
        return response;
    }
}
