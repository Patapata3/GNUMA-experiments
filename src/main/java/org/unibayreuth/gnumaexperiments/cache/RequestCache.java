package org.unibayreuth.gnumaexperiments.cache;

import java.net.http.HttpResponse;

public interface RequestCache {
    void add(String uri, HttpResponse<String> response);
    boolean contains(String uri);
    HttpResponse<String> get(String uri);
    void invalidate(String key);
}
