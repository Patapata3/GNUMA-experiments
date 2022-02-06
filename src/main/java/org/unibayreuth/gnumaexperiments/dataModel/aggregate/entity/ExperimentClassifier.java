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
    @EntityId(routingKey = "classifierId")
    private UUID id;
    private String status;
    private String remoteId;
    private String address;
    private Model model;
    private Map<String, List<Double>> results = new HashMap<>();
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
        status = !Objects.isNull(event.getStatus()) ? event.getStatus().getId() : null;
        if (!Objects.isNull(event.getNewResults())) {
            event.getNewResults().forEach((key, value) -> {
                if (!results.containsKey(key)) {
                    results.put(key, new ArrayList<>());
                }
                results.get(key).add(value);
            });
        }
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

    public Map<String, List<Double>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<Double>> results) {
        this.results = results;
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
