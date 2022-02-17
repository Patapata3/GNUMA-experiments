package org.unibayreuth.gnumaexperiments.events.classifiers;

public class DeletedClassifierEvent {
    private final String address;

    public DeletedClassifierEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "DeletedClassifierEvent{" +
                "address='" + address + '\'' +
                '}';
    }
}
