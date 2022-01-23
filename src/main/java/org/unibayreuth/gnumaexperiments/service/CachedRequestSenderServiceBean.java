package org.unibayreuth.gnumaexperiments.service;

import org.springframework.stereotype.Service;
import org.unibayreuth.gnumaexperiments.cache.RequestCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * Service, responsible for sending requests, responses of which are stored in cache
 */
@Service("gnuma_CachedSenderService")
public class CachedRequestSenderServiceBean extends RequestSenderServiceBean {
    private final RequestCache requestCache;

    @Autowired
    public CachedRequestSenderServiceBean(RequestCache requestCache) {
        this.requestCache = requestCache;
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
    public HttpResponse<String> sendGetRequest(String uri, String... headers) throws IOException, InterruptedException, ServiceRequestException {
        if (requestCache.contains(uri)) {
            return requestCache.get(uri);
        }

        HttpResponse<String> response =  super.sendGetRequest(uri, headers);
        requestCache.add(uri, response);
        return response;
    }
}
