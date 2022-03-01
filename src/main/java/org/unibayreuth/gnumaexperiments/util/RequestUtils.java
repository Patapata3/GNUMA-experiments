package org.unibayreuth.gnumaexperiments.util;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class RequestUtils {
    /**
     * Construct request path by the uri of the resource and query parrameters
     * @param baseUrl - uri of the resource
     * @param path - request path
     * @param params - map of query parameters with their values
     * @return - final address to which request is going to be sent
     */
    public static String constructUrlWithParameters(String baseUrl, String path, Map<String, String> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(path);
        params.forEach(uriComponentsBuilder::queryParam);
        return uriComponentsBuilder.build().toUriString();
    }
}
