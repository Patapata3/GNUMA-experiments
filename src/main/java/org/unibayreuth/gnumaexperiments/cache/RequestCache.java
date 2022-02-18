package org.unibayreuth.gnumaexperiments.cache;

import java.net.http.HttpResponse;

public interface RequestCache {
    /**
     * Add a record to the cache
     * @param uri - address of a requested resource
     * @param response - response being cached
     */
    void add(String uri, HttpResponse<String> response);

    /**
     * Check if a record is present in the cache
     * @param uri - address of a requested resource
     * @return - true if the record exists, false if it doesn't
     */
    boolean contains(String uri);

    /**
     * Get a record from cache
     * @param uri - address of a requested resource
     * @return - cached response
     */
    HttpResponse<String> get(String uri);
    
    /**
     * Invalidate a record in cache
     * @param key - key of the record being invalidated
     */
    void invalidate(String key);
}
