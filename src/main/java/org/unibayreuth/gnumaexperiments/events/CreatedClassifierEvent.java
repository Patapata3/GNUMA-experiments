package org.unibayreuth.gnumaexperiments.events;

import java.util.UUID;

public class CreatedClassifierEvent {
    private final UUID id;
    private String name;
    private String address;

    public CreatedClassifierEvent(UUID id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return String.format("CreatedClassifierEvent{id=%s}", id.toString());
    }
}
