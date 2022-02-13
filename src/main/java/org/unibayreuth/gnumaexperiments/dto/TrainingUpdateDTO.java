package org.unibayreuth.gnumaexperiments.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class TrainingUpdateDTO {
    @SerializedName(value = "model_id", alternate = "modelId")
    private UUID modelId;
    private String address;
    @SerializedName(value = "current_step", alternate = "currentStep")
    private int currentStep;
    @SerializedName(value = "total_steps", alternate = "totalSteps")
    private int totalSteps;
    private boolean finished;
    private List<MetricDTO> metrics;

    public TrainingUpdateDTO(UUID modelId, String address, int currentStep, int totalSteps, boolean finished, List<MetricDTO> metrics) {
        this.modelId = modelId;
        this.address = address;
        this.currentStep = currentStep;
        this.totalSteps = totalSteps;
        this.finished = finished;
        this.metrics = metrics;
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

    public String getAddress() {
        return address;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public int getTotalSteps() {
        return totalSteps;
    }
}
