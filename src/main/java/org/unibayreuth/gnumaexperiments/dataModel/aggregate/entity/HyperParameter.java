package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.modelling.command.EntityId;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.HyperParameterType;

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

    public HyperParameter(String key, HyperParameterType type, boolean optional, String defaultValue, List<String> valueList) {
        this.id = UUID.randomUUID();
        this.key = key;
        this.type = type.getId();
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

    public HyperParameterType getType() {
        return type == null ? null :
                HyperParameterType.valueOf(type);
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
