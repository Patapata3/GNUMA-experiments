package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class ClassifierInterruptDTO {
    private String address;
    @SerializedName(value = "model_id")
    private UUID modelId;
    @SerializedName(value = "paused", alternate = "pause")
    private boolean pause;

    public ClassifierInterruptDTO(String address, UUID modelId, boolean pause) {
        this.address = address;
        this.modelId = modelId;
        this.pause = pause;
    }

    public String getAddress() {
        return address;
    }

    public UUID getModelId() {
        return modelId;
    }

    public boolean isPause() {
        return pause;
    }
}
