package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ResultSourceType;

import java.util.Map;
import java.util.UUID;

public class EvalFinishDTO {
    private String classifierId;
    private String address;
    @SerializedName(value = "model_id", alternate = "modelId")
    private UUID modelId;
    private UUID resultSourceId;
    private ResultSourceType resultSourceType;
    private Map<String, Double> results;

    public EvalFinishDTO(String classifierId, String address, UUID modelId, UUID resultSourceId, ResultSourceType resultSourceType, Map<String, Double> results) {
        this.classifierId = classifierId;
        this.address = address;
        this.modelId = modelId;
        this.resultSourceId = resultSourceId;
        this.resultSourceType = resultSourceType;
        this.results = results;
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

    public Map<String, Double> getResults() {
        return results;
    }
}
