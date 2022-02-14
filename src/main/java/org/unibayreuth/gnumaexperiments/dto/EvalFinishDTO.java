package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;
import org.unibayreuth.gnumaexperiments.dataModel.enums.ResultSourceType;

import java.util.List;
import java.util.UUID;

public class EvalFinishDTO {
    private String classifierId;
    private String address;
    @SerializedName(value = "model_id", alternate = "modelId")
    private UUID modelId;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;
    private List<MetricDTO> metrics;

    public EvalFinishDTO(String classifierId, String address, UUID modelId, UUID resultSourceId, ResultSourceType resultSourceType, List<MetricDTO> metrics) {
        this.classifierId = classifierId;
        this.address = address;
        this.modelId = modelId;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
        this.metrics = metrics;
    }

    public String getClassifierId() {
        return classifierId;
    }

    public String getAddress() {
        return address;
    }

    public UUID getModelId() {
        return modelId;
    }

    public UUID getResultSourceId() {
        return resultSourceId;
    }

    public ResultSourceType getResultSourceType() {
        return resultSourceType;
    }

    public List<MetricDTO> getMetrics() {
        return metrics;
    }
}
