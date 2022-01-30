package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.List;
import java.util.UUID;

public class StartExperimentCommand {
    private UUID id;
    private UUID trainDatasetId;
    private UUID testDatasetId;
    private List<ExperimentClassifierDTO> experimentClassifierDTOList;

    public StartExperimentCommand(UUID id, UUID trainDatasetId, UUID testDatasetId, List<ExperimentClassifierDTO> experimentClassifierDTOList) {
        this.id = id;
        this.trainDatasetId = trainDatasetId;
        this.testDatasetId = testDatasetId;
        this.experimentClassifierDTOList = experimentClassifierDTOList;
    }

    public UUID getTrainDatasetId() {
        return trainDatasetId;
    }

    public UUID getTestDatasetId() {
        return testDatasetId;
    }

    public List<ExperimentClassifierDTO> getExperimentClassifierDTOList() {
        return experimentClassifierDTOList;
    }

    @Override
    public String toString() {
        return "StartExperimentCommand{" +
                "id=" + id +
                '}';
    }
}
