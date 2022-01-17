package org.unibayreuth.gnumaexperiments.restapi.commands;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.commands.classifiers.CreateClassifierCommand;
import org.unibayreuth.gnumaexperiments.commands.classifiers.DeleteClassifierCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.HyperParameter;
import org.unibayreuth.gnumaexperiments.dto.ClassifierDTO;
import org.unibayreuth.gnumaexperiments.dto.HyperParameterDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/classifiers")
public class ClassifierCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ClassifierCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @CrossOrigin
    @PostMapping
    public CompletableFuture<String> createClassifier(@RequestBody ClassifierDTO newClassifier) {
        List<HyperParameter> hyperParameters = Objects.isNull(newClassifier) ? new ArrayList<>() :
                newClassifier.getHyperParameters()
                        .stream()
                        .map(this::createHyperParameterFromDTO)
                        .collect(Collectors.toList());
        return commandGateway.send(new CreateClassifierCommand(newClassifier.getId(), newClassifier.getAddress(), hyperParameters));
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public CompletableFuture<String> deleteClassifier(@PathVariable String id) {
        return commandGateway.send(new DeleteClassifierCommand(id));
    }

    private HyperParameter createHyperParameterFromDTO(HyperParameterDTO hyperParameterDTO) {
        return new HyperParameter(hyperParameterDTO.getKey(), hyperParameterDTO.getType(), hyperParameterDTO.isOptional(),
                hyperParameterDTO.getDefaultValue(), hyperParameterDTO.getValueList());
    }
}
