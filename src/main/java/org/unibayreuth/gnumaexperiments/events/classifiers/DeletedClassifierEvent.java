package org.unibayreuth.gnumaexperiments.events.classifiers;

public class DeletedClassifierEvent {
    private final String id;

    public DeletedClassifierEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DeletedClassifierEvent{" +
                "id='" + id + '\'' +
                '}';
    }
}
