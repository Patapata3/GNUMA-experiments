package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ResultSourceType;

import java.util.Map;
import java.util.UUID;

public class EvalFinishDTO {
    private String classifierId;
    @SerializedName(value = "model_id", alternate = "modelId")
    private UUID modelId;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;
    private Map<String, Double> results;

    public EvalFinishDTO(String classifierId, UUID modelId, UUID resultSourceId, ResultSourceType resultSourceType, Map<String, Double> results) {
        this.classifierId = classifierId;
        this.modelId = modelId;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
        this.results = results;
    }

    public String getClassifierId() {
        return classifierId;
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

    public Map<String, Double> getResults() {
        return results;
    }
}
