package org.unibayreuth.gnumaexperiments.commands.experiments;

import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;

import java.util.List;
import java.util.UUID;

public class StartExperimentCommand {
    private UUID id;
    private DataConfig data;
    private String description;
    private List<ExperimentClassifierDTO> experimentClassifierDTOList;

    public StartExperimentCommand(UUID id, DataConfig data, String description, List<ExperimentClassifierDTO> experimentClassifierDTOList) {
        this.id = id;
        this.data = data;
        this.description = description;
        this.experimentClassifierDTOList = experimentClassifierDTOList;
    }

    public UUID getId() {
        return id;
    }

    public DataConfig getData() {
        return data;
    }

    public String getDescription() {
        return description;
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
