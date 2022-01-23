package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class ExperimentUpdateDTO {
    private String classifierId;
    @SerializedName(value = "model_id", alternate = "modelId")
    private UUID modelId;
    private boolean finished;
    private List<MetricDTO> metrics;

    public ExperimentUpdateDTO(String classifierId, UUID modelId, boolean finished, List<MetricDTO> metrics) {
        this.classifierId = classifierId;
        this.modelId = modelId;
        this.finished = finished;
        this.metrics = metrics;
    }

    public String getClassifierId() {
        return classifierId;
    }

    public UUID getModelId() {
        return modelId;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<MetricDTO> getMetrics() {
        return metrics;
    }
}
