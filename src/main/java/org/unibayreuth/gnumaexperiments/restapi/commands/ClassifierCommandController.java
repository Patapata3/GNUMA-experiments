package org.unibayreuth.gnumaexperiments.restapi.commands;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.commands.classifiers.CreateClassifierCommand;
import org.unibayreuth.gnumaexperiments.commands.classifiers.DeleteClassifierCommand;
import org.unibayreuth.gnumaexperiments.dataModel.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.ClassifierDTO;
import org.unibayreuth.gnumaexperiments.service.DTOConverterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "api/v1/classifier")
public class ClassifierCommandController {

    private final CommandGateway commandGateway;
    private final DTOConverterService dtoConverterService;

    @Autowired
    public ClassifierCommandController(CommandGateway commandGateway, DTOConverterService dtoConverterService) {
        this.commandGateway = commandGateway;
        this.dtoConverterService = dtoConverterService;
    }

    @CrossOrigin
    @PostMapping
    public CompletableFuture<String> createClassifier(@RequestBody ClassifierDTO newClassifier) {
        List<HyperParameter> hyperParameters = Objects.isNull(newClassifier) ? new ArrayList<>() :
                dtoConverterService.createHyperParametersFromDTO(newClassifier.getHyperParameters());

        return commandGateway.send(new CreateClassifierCommand(newClassifier.getClassifierName(), newClassifier.getAddress(), hyperParameters));
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public CompletableFuture<String> deleteClassifier(@PathVariable String id) {
        return commandGateway.send(new DeleteClassifierCommand(id));
    }
}
