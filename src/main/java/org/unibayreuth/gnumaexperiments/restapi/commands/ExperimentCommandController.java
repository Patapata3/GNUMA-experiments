package org.unibayreuth.gnumaexperiments.restapi.commands;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.commands.experiments.PauseExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.ResumeExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.StartExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.StopExperimentCommand;
import org.unibayreuth.gnumaexperiments.dto.ExperimentDTO;
import org.unibayreuth.gnumaexperiments.handlers.exceptionhandling.ExperimentError;
import org.unibayreuth.gnumaexperiments.handlers.exceptionhandling.ExperimentErrorCode;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@RestController
@RequestMapping(value = "/experiment")
public class ExperimentCommandController {
    private final Logger log = LoggerFactory.getLogger(ExperimentCommandController.class);

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
                .exceptionally(e -> formErrorResponse(e.getCause(), "start an experiment"));
    }

    @CrossOrigin
    @PutMapping("/pause/{id}")
    public CompletableFuture<ResponseEntity<String>> pauseExperiment(@PathVariable UUID id) {
        return commandGateway.send(new PauseExperimentCommand(id))
                .thenApply(it -> ResponseEntity.ok(""))
                .exceptionally(e -> formErrorResponse(e.getCause(), "pause an experiment"));
    }

    @CrossOrigin
    @PutMapping("/stop/{id}")
    public CompletableFuture<ResponseEntity<String>> stopExperiment(@PathVariable UUID id) {
        return commandGateway.send(new StopExperimentCommand(id))
                .thenApply(it -> ResponseEntity.ok(""))
                .exceptionally(e -> formErrorResponse(e.getCause(), "stop an experiment"));
    }

    @CrossOrigin
    @PutMapping("/resume/{id}")
    public CompletableFuture<ResponseEntity<String>> resumeExperiment(@PathVariable UUID id) {
        return commandGateway.send(new ResumeExperimentCommand(id))
                .thenApply(it -> ResponseEntity.ok(""))
                .exceptionally(e -> formErrorResponse(e.getCause(), "resume an experiment"));
    }

    private ResponseEntity<String> formErrorResponse(Throwable e, String failedAction) {
        if (e instanceof CommandExecutionException) {
            return ResponseEntity.badRequest()
                    .body(formErrorMessage((CommandExecutionException) e, failedAction));
        }
        String errorMessage = String.format("Unable to start %s due to unknown generic exception: %s", failedAction, e.getMessage());
        log(log::error, errorMessage, e);
        return ResponseEntity.internalServerError().body(errorMessage);
    }

    private String formErrorMessage(CommandExecutionException e, String failedAction) {
        Map<ExperimentErrorCode, String> reasonMap = Map.of(
                ExperimentErrorCode.ENTITY_NOT_FOUND, "a missing entity",
                ExperimentErrorCode.VALIDATION_ERROR, "validation constraints",
                ExperimentErrorCode.REQUEST_ERROR, "failed request to a classifier",
                ExperimentErrorCode.UNKNOWN, "unknown exception"
        );

        return e.getDetails()
                .map(it -> {
                    ExperimentError error = (ExperimentError) it;
                    String message = String.format("Unable to %s due to %s: %s", failedAction,
                            reasonMap.get(error.getErrorCode()),
                            error.getMessage());

                    log(log::error, message, e);
                    return message;
                }).orElseGet(() -> {
            String message = String.format("Unable to %s due to: %s", failedAction, e.getMessage());
            log(log::error, message, e);
            return message;
        });
    }
}
