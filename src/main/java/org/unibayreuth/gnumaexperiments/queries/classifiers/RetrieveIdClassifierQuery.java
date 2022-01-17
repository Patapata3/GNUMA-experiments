package org.unibayreuth.gnumaexperiments.queries.classifiers;

public class RetrieveIdClassifierQuery {
    private String id;

    public RetrieveIdClassifierQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RetrieveIdClassifierQuery{" +
                "id='" + id + '\'' +
                '}';
    }
}
