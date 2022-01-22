package org.unibayreuth.gnumaexperiments.restapi.commands;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.commands.experiments.StartExperimentCommand;
import org.unibayreuth.gnumaexperiments.dto.ExperimentDTO;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/experiment")
public class ExperimentCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ExperimentCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @CrossOrigin
    @PostMapping
    public CompletableFuture<ResponseEntity<String>> startExperiment(@RequestBody ExperimentDTO experimentDTO) {
        return commandGateway.send(new StartExperimentCommand(experimentDTO.getTrainDatasetId(), experimentDTO.getTestDatasetId(), experimentDTO.getClassifier()))
                .thenApply(it -> ResponseEntity.ok(""))
                .exceptionally(e -> ResponseEntity.internalServerError().body(e.getMessage()));
    }
}
