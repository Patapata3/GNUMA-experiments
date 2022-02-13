package org.unibayreuth.gnumaexperiments.dto;


import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class TrainRequestDTO {
    @SerializedName(value="train_ids")
    private List<String> trainIds;
    @SerializedName(value="val_ids")
    private List<String> valIds;
    @SerializedName(value="model_name")
    private String modelName;
    @SerializedName(value="hyper_parameters")
    private Map<String, String> hyperParameters;

    public TrainRequestDTO(List<String> trainIds, List<String> valIds, String modelName, Map<String, String> hyperParameters) {
        this.trainIds = trainIds;
        this.valIds = valIds;
        this.modelName = modelName;
        this.hyperParameters = hyperParameters;
    }

    public List<String> getTrainIds() {
        return trainIds;
    }

    public List<String> getValIds() {
        return valIds;
    }

    public String getModelName() {
        return modelName;
    }

    public Map<String, String> getHyperParameters() {
        return hyperParameters;
    }
}
