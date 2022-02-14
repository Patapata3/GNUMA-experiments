package org.unibayreuth.gnumaexperiments.handlers.commandhandling;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.commands.experiments.*;
import org.unibayreuth.gnumaexperiments.dataModel.entity.DataConfig;
import org.unibayreuth.gnumaexperiments.dataModel.entity.DataSplit;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.dataModel.entity.Model;
import org.unibayreuth.gnumaexperiments.dataModel.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.dto.DatasetDTO;
import org.unibayreuth.gnumaexperiments.dto.ExperimentClassifierDTO;
import org.unibayreuth.gnumaexperiments.dto.TrainRequestDTO;
import org.unibayreuth.gnumaexperiments.exceptions.ExperimentValidationException;
import org.unibayreuth.gnumaexperiments.exceptions.MissingEntityException;
import org.unibayreuth.gnumaexperiments.exceptions.ServiceRequestException;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAddressListClassifierQuery;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveIdExperimentQuery;
import org.unibayreuth.gnumaexperiments.service.RequestSenderServiceBean;
import org.unibayreuth.gnumaexperiments.service.ValidationService;
import org.unibayreuth.gnumaexperiments.util.RequestUtils;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Value("${dataset.service.address}")
    private String datasetServiceAddress;

    @CommandHandler
    public void handle(StartExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException, IOException, InterruptedException, ServiceRequestException {
        log(log::info, "Handling command to start an experiment");
        List<ExperimentClassifierDTO> classifierDTOList = cmd.getExperimentClassifierDTOList();
        List<String> addresses = classifierDTOList
                .stream()
                .map(ExperimentClassifierDTO::getAddress)
                .collect(Collectors.toList());
        List<ClassifierView> classifiers = queryGateway.query(new RetrieveAddressListClassifierQuery(addresses),
                multipleInstancesOf(ClassifierView.class)).join();

        Map<String, ClassifierView> classifierMap = classifiers
                .stream()
                .collect(Collectors.toMap(ClassifierView::getAddress, value -> value));
        checkClassifierPresence(classifierMap, classifierDTOList);

        log(log::info, "Start of the validation of experiment parameters");
        for(ExperimentClassifierDTO classifierDTO : classifierDTOList) {
            validationService.validateHyperParameters(classifierDTO.getHyperParameterValues(), classifierMap.get(classifierDTO.getAddress()).getHyperParameters());
        }
        log(log::info, "End of experiment parameter validation");

        log(log::info, "Collecting split data from the dataset service");
        DataConfig data = cmd.getData();
        String url = RequestUtils.constructUrlWithParameters(datasetServiceAddress, String.format("/dataset/%s", data.getDatasetId()),
                Map.of("validationSplit", data.getValidationSplit().toString(), "testSplit", data.getTestSplit().toString(), "seed", data.getSeed().toString()));
        DatasetDTO splitDataset = new Gson().fromJson(requestSenderService.sendGetRequest(url).body(), DatasetDTO.class);
        data.setDataSplit(new DataSplit(UUID.randomUUID(), splitDataset.getData().getFolds().get(0).getTrain(), splitDataset.getData().getFolds().get(0).getValid(), splitDataset.getData().getTest()));
        log(log::info, "Collecting split data from the dataset service finished");

        List<ExperimentClassifier> startedClassifiers = startTrainingWithHandler(data.getDataSplit(), cmd, classifierMap, classifierDTOList);
        commandGateway.send(new CreateExperimentCommand(cmd.getId(), startedClassifiers, data, cmd.getDescription()));
        log(log::info, "New experiment successfully created");
    }

    @CommandHandler
    public void handle(PauseExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException {
        log(log::info, String.format("Received command to pause an experiment with id {%s}", cmd.getId()));
        ExperimentView runningExperiment = queryExperimentWithHandler(cmd.getId());

        List<ExperimentClassifier> trainingClassifiers = getClassifiersFromListByCondition(cmd.getPausedClassifiers(), runningExperiment,
                classifier -> classifier.getStatus() == ExperimentStatus.TRAIN);

        if (trainingClassifiers.isEmpty()) {
            String errorMessage = "Could not find training classifiers for this experiment";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        for (ExperimentClassifier classifier : trainingClassifiers) {
            try {
                String classifierId = classifier.getRemoteId();
                UUID modelId = classifier.getModel().getRemoteId();
                String address = classifier.getAddress();
                log(log::info, String.format("Sending request to classifier {%s} by address {%s} to pause a model {%s}", classifierId, address, modelId));
                requestSenderService.sendPutRequest(String.format("%s/pause/%s", address, modelId), null);
                log(log::info, "Sending command to update the experiment");
                commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), classifier.getId(), ExperimentStatus.PAUSE, new HashMap<>()));
                log(log::info, String.format("Experiment {%s} Classifier {%s} is paused", runningExperiment.getId(), classifierId));
            } catch (ServiceRequestException | InterruptedException | IOException e) {
                log(log::error, e.getMessage(), e);
            }
        }
    }

    @CommandHandler
    public void handle(StopExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException {
        log(log::info, String.format("Received command to stop an experiment with id {%s}", cmd.getId()));
        ExperimentView runningExperiment = queryExperimentWithHandler(cmd.getId());

        Set<ExperimentStatus> illegalStatuses = Sets.newHashSet(ExperimentStatus.FINISH, ExperimentStatus.ERROR, ExperimentStatus.STOP);
        List<ExperimentClassifier> runningClassifiers = getClassifiersFromListByCondition(cmd.getStoppedClassifiers(), runningExperiment,
                classifier -> !illegalStatuses.contains(classifier.getStatus()));

        if (runningClassifiers.isEmpty()) {
            String errorMessage = "Could not find running classifiers for this experiment";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        for (ExperimentClassifier classifier : runningClassifiers) {
            try {
                String classifierId = classifier.getRemoteId();
                UUID modelId = classifier.getModel().getRemoteId();
                String address = classifier.getAddress();
                log(log::info, String.format("Sending request to classifier {%s} by address {%s} to interrupt a model {%s}", classifierId, address, modelId));
                requestSenderService.sendDeleteRequest(String.format("%s/interrupt/%s", classifier.getAddress(), modelId));
                log(log::info, "Sending command to update the experiment");
                commandGateway.send(new UpdateExperimentCommand(runningExperiment.getId(), classifier.getId(), ExperimentStatus.STOP, new HashMap<>()));
                log(log::info, String.format("Experiment {%s} Classifier {%s} is stopped", runningExperiment.getId(), classifierId));
            } catch (ServiceRequestException | InterruptedException | IOException e) {
                log(log::error, e.getMessage(), e);
            }
        }
    }

    @CommandHandler
    public void handle(ResumeExperimentCommand cmd) throws MissingEntityException, ExperimentValidationException {
        log(log::info, String.format("Received command to resume an experiment with id {%s}", cmd.getId()));
        ExperimentView pausedExperiment = queryExperimentWithHandler(cmd.getId());

        List<ExperimentClassifier> pausedClassifiers = getClassifiersFromListByCondition(cmd.getResumedClassifiers(), pausedExperiment,
                classifier -> classifier.getStatus() == ExperimentStatus.PAUSE);

        if (pausedClassifiers.isEmpty()) {
            String errorMessage = "Could not find paused experiments";
            log(log::error, errorMessage);
            throw new ExperimentValidationException(errorMessage);
        }

        for (ExperimentClassifier classifier : pausedClassifiers) {
            try {
                String classifierId = classifier.getRemoteId();
                UUID modelId = classifier.getModel().getRemoteId();
                String address = classifier.getAddress();
                log(log::info, String.format("Sending request to classifier {%s} by address {%s} to continue training of a model {%s}", classifierId, address, modelId));
                requestSenderService.sendPostRequest(String.format("%s/continue/%s", address, modelId), null);
                log(log::info, "Sending command to update the experiment");
                commandGateway.send(new UpdateExperimentCommand(pausedExperiment.getId(), classifier.getId(), ExperimentStatus.TRAIN, new HashMap<>()));
                log(log::info, String.format("Experiment {%s} is resumed", pausedExperiment.getId()));
            } catch (ServiceRequestException | IOException | InterruptedException e) {
                log(log::error, e.getMessage(), e);
            }
        }
    }

    private ExperimentView queryExperimentWithHandler(UUID experimentId) throws MissingEntityException {
        ExperimentView runningExperiment = queryGateway.query(new RetrieveIdExperimentQuery(experimentId),
                instanceOf(ExperimentView.class)).join();
        if (Objects.isNull(runningExperiment)) {
            log(log::error, String.format("No experiment found with Id {%s}", experimentId));
            throw new MissingEntityException(experimentId, "experiment");
        }
        return runningExperiment;
    }

    private void checkClassifierPresence(Map<String, ClassifierView> classifierMap, List<ExperimentClassifierDTO> classifierDTOList) throws MissingEntityException {
        String[] missingAddresses = classifierDTOList
                .stream()
                .map(ExperimentClassifierDTO::getAddress)
                .filter(address -> !classifierMap.containsKey(address))
                .toArray(String[]::new);
        if (missingAddresses.length > 0) {
            String errorMessage = String.format("Classifier with addresses in %s not found", Arrays.toString(missingAddresses));
            log(log::error, errorMessage);
            throw new MissingEntityException(errorMessage);
        }
    }

    private List<ExperimentClassifier> startTrainingWithHandler(DataSplit dataSplit, StartExperimentCommand cmd, Map<String, ClassifierView> classifierMap, List<ExperimentClassifierDTO> classifierDTOList) {
        List<ExperimentClassifier> startedClassifiers = new ArrayList<>();
        for (ExperimentClassifierDTO classifierDTO : classifierDTOList) {
            try {
                ClassifierView classifier = classifierMap.get(classifierDTO.getAddress());
                log(log::info, String.format("Sending request to start training on the classifier %s to %s", classifier.getId(), classifier.getAddress()));
                TrainRequestDTO trainRequest = new TrainRequestDTO(dataSplit.getTrainData(), dataSplit.getValidationData(), cmd.getDescription(), classifierDTO.getHyperParameterValues(), cmd.getData().getDatasetId());
                HttpResponse<String> classifierResponse = requestSenderService.sendPostRequest(String.format("%s/train", classifier.getAddress()),
                        new Gson().toJson(trainRequest), "Content-type", "application/json");
                JSONObject jsonBody = new JSONObject(classifierResponse.body());

                UUID modelId = UUID.fromString(jsonBody.getString("model_id"));
                log(log::info, String.format("Training started, created model with id: %s", modelId.toString()));
                Model model = new Model(UUID.randomUUID(), modelId, classifierDTO.getHyperParameterValues());
                ExperimentClassifier experimentClassifier = new ExperimentClassifier(UUID.randomUUID(), ExperimentStatus.TRAIN, classifierDTO.getId(), classifierDTO.getAddress(), model);
                startedClassifiers.add(experimentClassifier);
            } catch (ServiceRequestException | IOException | InterruptedException e) {
                log(log::error, e.getMessage(), e);
                ExperimentClassifier experimentClassifier = new ExperimentClassifier(UUID.randomUUID(), ExperimentStatus.ERROR, classifierDTO.getId(), classifierDTO.getAddress(), null);
                startedClassifiers.add(experimentClassifier);
            }
        }
        return startedClassifiers;
    }

    private List<ExperimentClassifier> getClassifiersFromListByCondition(List<ExperimentClassifierDTO> commandClassifiers, ExperimentView runningExperiment, Predicate<? super ExperimentClassifier> condition) {
        Map<String, ExperimentClassifier> classifierMap = runningExperiment.getClassifiers()
                .stream()
                .filter(condition)
                .collect(Collectors.toMap(ExperimentClassifier::getAddress, value -> value));

        return commandClassifiers
                .stream()
                .filter(classifierDTO -> classifierMap.containsKey(classifierDTO.getAddress()))
                .map(classifierDTO -> classifierMap.get(classifierDTO.getAddress()))
                .collect(Collectors.toList());
    }
}
