package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.UUID;

public class StartExperimentCommand {
    private UUID trainDatasetId;
    private UUID testDatasetId;
    private ExperimentClassifierDTO experimentClassifierDTO;

    public StartExperimentCommand(UUID trainDatasetId, UUID testDatasetId, ExperimentClassifierDTO experimentClassifierDTO) {
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
        this.experimentClassifierDTO = experimentClassifierDTO;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }

    public ExperimentClassifierDTO getExperimentClassifierDTO() {
        return experimentClassifierDTO;
    }
}
