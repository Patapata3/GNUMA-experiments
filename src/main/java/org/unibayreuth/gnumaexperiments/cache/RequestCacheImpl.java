package org.unibayreuth.gnumaexperiments.cache;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing request cache as a hashmap
 */
@Component
public class RequestCacheImpl implements RequestCache {
    private Map<String, HttpResponse<String>> cacheMap = new HashMap<>();

    /**
     * Add a record to the cache
     * @param uri - address of a requested resource
     * @param response - response being cached
     */
    @Override
    public void add(String uri, HttpResponse<String> response) {
        cacheMap.put(uri, response);
    }

    /**
     * Check if a record is present in the cache
     * @param uri - address of a requested resource
     * @return - true if the record exists, false if it doesn't
     */
    @Override
    public boolean contains(String uri) {
        return cacheMap.containsKey(uri);
    }

    /**
     * Get a record from cache
     * @param uri - address of a requested resource
     * @return - cached response
     */
    @Override
    public HttpResponse<String> get(String uri) {
        if (!cacheMap.containsKey(uri)) {
            throw new IllegalArgumentException(String.format("Entry with the key %s is not present in cache", uri));
        }
        return cacheMap.get(uri);
    }

    /**
     * Invalidate a record in cache
     * @param key - key of the record being invalidated
     */
    @Override
    public void invalidate(String key) {
        cacheMap.remove(key);
    }
}
