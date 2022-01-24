package org.unibayreuth.gnumaexperiments.handlers.commandhandling;

import com.google.gson.Gson;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.experiments.*;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.Model;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;
import org.unibayreuth.gnumaexperiments.dto.TrainRequestDTO;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;
import org.unibayreuth.gnumaexperiments.exceptions.MissingEntityException;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveIdClassifierQuery;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveIdExperimentQuery;
import org.unibayreuth.gnumaexperiments.service.RequestSenderServiceBean;
import org.unibayreuth.gnumaexperiments.service.ValidationService;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.messaging.responsetypes.ResponseTypes.*;
import static org.unibayreuth.gnumaexperiments.logging.GnumaLogger.*;

@Component
public class ExperimentCommandHandler {
    private final Logger log = LoggerFactory.getLogger(ExperimentCommandHandler.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private RequestSenderServiceBean requestSenderService;

    @CommandHandler
    public void handle(StartExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException, IOException, InterruptedException, ServiceRequestException {
        log(log::info, "Handling command to start an experiment");
        ClassifierView classifier = queryGateway.query(new RetrieveIdClassifierQuery(cmd.getExperimentClassifierDTO().getId()),
                instanceOf(ClassifierView.class)).join();
        if (Objects.isNull(classifier)) {
            log(log::error, String.format("Classifier with id = %s not found", cmd.getExperimentClassifierDTO().getId()));
            throw new MissingEntityException(cmd.getExperimentClassifierDTO().getId(), "classifier");
        }

        log(log::info, "Start of the validation of experiment parameters");
        ExperimentClassifierDTO classifierDTO = cmd.getExperimentClassifierDTO();
        validationService.validateHyperParameters(classifierDTO.getHyperParameterValues(), classifier.getHyperParameters());
        log(log::info, "End of experiment parameter validation");

        log(log::info, String.format("Sending request to start training on the classifier %s to %s", classifier.getId(), classifier.getAddress()));
        TrainRequestDTO trainRequest = new TrainRequestDTO(cmd.getTrainDatasetId(), classifierDTO.getHyperParameterValues());
        HttpResponse<String> classifierResponse = requestSenderService.sendPostRequest(String.format("%s/train", classifier.getAddress()),
                new Gson().toJson(trainRequest), "Content-type", "application/json");
        JSONObject jsonBody = new JSONObject(classifierResponse.body());

        UUID modelId = UUID.fromString(jsonBody.getString("model_id"));
        log(log::info, String.format("Training started, created model with id: %s", modelId.toString()));
        Model model = new Model(UUID.randomUUID(), modelId, classifierDTO.getHyperParameterValues());
        ExperimentClassifier experimentClassifier = new ExperimentClassifier(UUID.randomUUID(), classifierDTO.getId(), classifierDTO.getAddress(), model);
        commandGateway.send(new CreateExperimentCommand(ExperimentStatus.TRAIN, experimentClassifier, cmd.getTrainDatasetId(), cmd.getTestDatasetId()));
        log(log::info, "New experiment successfully created");
    }

    @CommandHandler
    public void handle(PauseExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException, InterruptedException, ServiceRequestException, IOException {
        log(log::info, String.format("Received command to pause an experiment with id {%s}", cmd.getId()));
        ExperimentView runningExperiment = queryExperimentWithHandler(cmd.getId());

        if (runningExperiment.getStatus() != ExperimentStatus.TRAIN) {
            String errorMessage = "It is only possible to pause an experiment during its training phase";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        String classifierId = runningExperiment.getClassifier().getRemoteId();
        UUID modelId = runningExperiment.getClassifier().getModel().getRemoteId();
        log(log::info, String.format("Sending request to classifier {%s} to pause a model {%s}", classifierId, modelId));
        requestSenderService.sendPutRequest(String.format("%s/pause/%s", runningExperiment.getClassifier().getAddress(), modelId), null);
        log(log::info, "Sending command to update the experiment");
        commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), ExperimentStatus.PAUSE, new HashMap<>()));
        log(log::info, String.format("Experiment {%s} is paused", runningExperiment.getId()));
    }

    @CommandHandler
    public void handle(StopExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException, InterruptedException, ServiceRequestException, IOException {
        log(log::info, String.format("Received command to stop an experiment with id {%s}", cmd.getId()));
        ExperimentView runningExperiment = queryExperimentWithHandler(cmd.getId());

        if (runningExperiment.getStatus() == ExperimentStatus.FINISH) {
            String errorMessage = "It is impossible to stop a finished experiment";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        String classifierId = runningExperiment.getClassifier().getRemoteId();
        UUID modelId = runningExperiment.getClassifier().getModel().getRemoteId();
        log(log::info, String.format("Sending request to classifier {%s} to interrupt a model {%s}", classifierId, modelId));
        requestSenderService.sendDeleteRequest(String.format("%s/interrupt/%s", runningExperiment.getClassifier().getAddress(), modelId));
        log(log::info, "Sending command to update the experiment");
        commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), ExperimentStatus.STOP, new HashMap<>()));
        log(log::info, String.format("Experiment {%s} is stopped", runningExperiment.getId()));
    }

    @CommandHandler
    public void handle(ResumeExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException, InterruptedException, ServiceRequestException, IOException {
        log(log::info, String.format("Received command to resume an experiment with id {%s}", cmd.getId()));
        ExperimentView pausedExperiment = queryExperimentWithHandler(cmd.getId());

        if (pausedExperiment.getStatus() != ExperimentStatus.PAUSE) {
            String errorMessage = "It is only possible to resume a paused experiment";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }
        String classifierId = pausedExperiment.getClassifier().getRemoteId();
        UUID modelId = pausedExperiment.getClassifier().getModel().getRemoteId();
        log(log::info, String.format("Sending request to classifier {%s} to continue training of a model {%s}", classifierId, modelId));
        requestSenderService.sendPostRequest(String.format("%s/continue/%s", pausedExperiment.getClassifier().getAddress(), modelId), null);
        log(log::info, "Sending command to update the experiment");
        commandGateway.send(new UpdateExperimentCommand(pausedExperiment.getId(), ExperimentStatus.TRAIN, new HashMap<>()));
        log(log::info, String.format("Experiment {%s} is resumed", pausedExperiment.getId()));
    }

    private ExperimentView queryExperimentWithHandler(UUID experimentId) throws MissingEntityException {
        ExperimentView runningExperiment = queryGateway.query(new RetrieveIdExperimentQuery(experimentId),
                instanceOf(ExperimentView.class)).join();
        if (Objects.isNull(runningExperiment)) {
            log(log::error, String.format("No experiment found with Id {%s}", experimentId));
            throw new MissingEntityException(experimentId, "experiment");
        }
        return  runningExperiment;
    }
}
