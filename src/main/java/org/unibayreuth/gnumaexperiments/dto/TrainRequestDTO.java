package org.unibayreuth.gnumaexperiments.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrainRequestDTO {
    private List<UUID> data;
    private Map<String, String> hyperParameters;

    public TrainRequestDTO(List<UUID> data, Map<String, String> hyperParameters) {
        this.data = data;
        this.hyperParameters = hyperParameters;
    }

    public List<UUID> getData() {
        return data;
    }

    public Map<String, String> getHyperParameters() {
        return hyperParameters;
    }
}
