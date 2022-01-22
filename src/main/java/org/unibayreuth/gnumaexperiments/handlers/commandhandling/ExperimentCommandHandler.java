package org.unibayreuth.gnumaexperiments.handlers.commandhandling;

import com.google.gson.Gson;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.experiments.CreateExperimentCommand;
import org.unibayreuth.gnumaexperiments.commands.experiments.StartExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.Model;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;
import org.unibayreuth.gnumaexperiments.dto.TrainRequestDTO;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;
import org.unibayreuth.gnumaexperiments.exceptions.MissingClassifierException;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveIdClassifierQuery;
import org.unibayreuth.gnumaexperiments.service.RequestSenderServiceBean;
import org.unibayreuth.gnumaexperiments.service.ValidationService;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@Component
public class ExperimentCommandHandler {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private RequestSenderServiceBean requestSenderService;

    @CommandHandler
    public void handle(StartExperimentCommand cmd) throws MissingClassifierException, ExperimentValidationException, IOException, InterruptedException {
        ClassifierView classifier = queryGateway.query(new RetrieveIdClassifierQuery(cmd.getExperimentClassifierDTO().getId()),
                instanceOf(ClassifierView.class)).join();
        if (Objects.isNull(classifier)) {
            throw new MissingClassifierException(cmd.getExperimentClassifierDTO().getId());
        }

        ExperimentClassifierDTO classifierDTO = cmd.getExperimentClassifierDTO();
        validationService.validateHyperParameters(classifierDTO.getHyperParameterValues(), classifier.getHyperParameters());

        TrainRequestDTO trainRequest = new TrainRequestDTO(cmd.getTrainDatasetId(), classifierDTO.getHyperParameterValues());
        HttpResponse<String> classifierResponse = requestSenderService.sendPostRequest(String.format("%s/train", classifier.getAddress()),
                new Gson().toJson(trainRequest), "Content-type", "application/json");
        JSONObject jsonBody = new JSONObject(classifierResponse.body());

        UUID modelId = UUID.fromString(jsonBody.getString("model_id"));
        Model model = new Model(UUID.randomUUID(), modelId, classifierDTO.getHyperParameterValues());
        ExperimentClassifier experimentClassifier = new ExperimentClassifier(UUID.randomUUID(), classifierDTO.getId(), classifierDTO.getAddress(), model);
        commandGateway.send(new CreateExperimentCommand(ExperimentStatus.TRAIN, experimentClassifier, cmd.getTrainDatasetId(), cmd.getTestDatasetId()));
    }
}
