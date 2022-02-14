package org.unibayreuth.gnumaexperiments.dto;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrainRequestDTO {
    @SerializedName(value="train_ids")
    private List<String> trainIds;
    @SerializedName(value="val_ids")
    private List<String> valIds;
    @SerializedName(value="model_name")
    private String modelName;
    //TODO: implement getting label mappings from the dataset service as soon as it is finished there
    @SerializedName(value = "label_mapping")
    private Map<String, Integer> labelMapping = new HashMap<>();
    @SerializedName(value="hyper_parameters")
    private Map<String, String> hyperParameters;
    @SerializedName(value="dataset_id")
    private UUID datasetId;

    public TrainRequestDTO(List<String> trainIds, List<String> valIds, String modelName, Map<String, String> hyperParameters, UUID datasetId) {
        this.trainIds = trainIds;
        this.valIds = valIds;
        this.modelName = modelName;
        this.hyperParameters = hyperParameters;
        this.datasetId = datasetId;
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

    public UUID getDatasetId() {
        return datasetId;
    }

    public Map<String, String> getHyperParameters() {
        return hyperParameters;
    }
}
