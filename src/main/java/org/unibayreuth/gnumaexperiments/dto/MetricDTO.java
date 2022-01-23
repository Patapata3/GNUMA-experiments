package org.unibayreuth.gnumaexperiments.dto;

public class MetricDTO {
    private String key;
    private Double value;

    public MetricDTO(String key, Double value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Double getValue() {
        return value;
    }
}
