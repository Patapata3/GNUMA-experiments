package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;

import java.util.List;
import java.util.UUID;

public class HyperParameter {
    @EntityId(routingKey = "hyperParameterId")
    private UUID id;
    private String key;
    private String type;
    private boolean optional;
    private String defaultValue;
    private List<String> valueList;

    public HyperParameter(String key, String type, boolean optional, String defaultValue, List<String> valueList) {
        this.id = UUID.randomUUID();
        this.key = key;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.valueList = valueList;
    }

    public UUID getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public List<String> getValueList() {
        return valueList;
    }
}
