package org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;
import org.unibayreuth.gnumaexperiments.commands.experiments.UpdateExperimentCommand;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.events.experiments.UpdatedExperimentEvent;

import java.util.*;

import static java.util.Optional.ofNullable;

public class ExperimentClassifier {
    @EntityId(routingKey = "experimentClassifierId")
    private UUID id;
    private String status;
    private String remoteId;
    private String address;
    private Model model;
    private Map<String, List<Double>> trainResults = new HashMap<>();
    private Map<String, Double> testResults = new HashMap<>();
    private Integer currentStep = 0;
    private Integer totalSteps = 0;

    public ExperimentClassifier() {
    }

    public ExperimentClassifier(UUID id, ExperimentStatus status, String remoteId, String address, Model model) {
        this.id = id;
        this.status = status.getId();
        this.remoteId = remoteId;
        this.address = address;
        this.model = model;
    }

    @CommandHandler
    public void handle(UpdateExperimentCommand cmd) {
        AggregateLifecycle.apply(new UpdatedExperimentEvent(cmd.getId(), id, cmd.getStatus(), cmd.getNewResults(),
                cmd.getCurrentStep(), cmd.getTotalSteps(), cmd.getResultSourceId(), cmd.getResultSourceType()));
    }

    @EventSourcingHandler
    public void handle(UpdatedExperimentEvent event) {
        if (!Objects.isNull(event.getNewResults())) {
            if (getStatus() == ExperimentStatus.TRAIN) {
                event.getNewResults().forEach((key, value) -> {
                    if (!trainResults.containsKey(key)) {
                        trainResults.put(key, new ArrayList<>());
                    }
                    trainResults.get(key).add(value);
                });
            } else {
                testResults.putAll(event.getNewResults());
            }
        }

        status = !Objects.isNull(event.getStatus()) ? event.getStatus().getId() : null;
        ofNullable(event.getCurrentStep()).ifPresent(currentStep -> this.currentStep = currentStep);
        ofNullable(event.getTotalSteps()).ifPresent(totalSteps -> this.totalSteps = totalSteps);
    }

    public UUID getId() {
        return id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public String getAddress() {
        return address;
    }

    public Model getModel() {
        return model;
    }

    public Map<String, List<Double>> getTrainResults() {
        return trainResults;
    }

    public void setTrainResults(Map<String, List<Double>> trainResults) {
        this.trainResults = trainResults;
    }

    public Map<String, Double> getTestResults() {
        return testResults;
    }

    public void setTestResults(Map<String, Double> testResults) {
        this.testResults = testResults;
    }

    public ExperimentStatus getStatus() {
        return status == null ? null :
                ExperimentStatus.valueOf(status);
    }

    public void setStatus(ExperimentStatus status) {
        this.status = !Objects.isNull(status) ? status.getId() : null;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }
}
