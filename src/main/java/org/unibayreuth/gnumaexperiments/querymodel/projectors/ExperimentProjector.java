package org.unibayreuth.gnumaexperiments.querymodel.projectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.GNUMAConstants;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.enums.ExperimentStatus;
import org.unibayreuth.gnumaexperiments.events.experiments.CreatedExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.DeletedExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.StopExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.UpdatedExperimentEvent;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveAllExperimentsQuery;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveClassifierModelExperimentQuery;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveIdExperimentQuery;
import org.unibayreuth.gnumaexperiments.querymodel.repositories.ExperimentViewRepository;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.util.*;

@Component
public class ExperimentProjector {
    @Autowired
    private ExperimentViewRepository experimentViewRepository;
    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void handle(CreatedExperimentEvent event) {
        ExperimentView view = new ExperimentView(event.getId(), event.getDate(), event.getStatus(), event.getClassifier());
        experimentViewRepository.save(view);
    }

    @EventHandler
    public void handle(DeletedExperimentEvent event) {
        experimentViewRepository.deleteById(event.getId());
    }

    @EventHandler
    public void handle(StopExperimentEvent event) {
        experimentViewRepository.findById(event.getId()).ifPresent(experimentView -> {
            experimentView.setStatus(ExperimentStatus.STOP);
            experimentViewRepository.save(experimentView);
        });
    }

    @EventHandler
    public void handle(UpdatedExperimentEvent event) {
        experimentViewRepository.findById(event.getId()).ifPresent(experimentView -> {
            experimentView.setStatus(event.getStatus());
            if (!Objects.isNull(event.getNewResults())) {
                writeResults(experimentView, event.getNewResults());
            }
        });
    }

    @QueryHandler
    public List<ExperimentView> handle(RetrieveAllExperimentsQuery query) {
        return experimentViewRepository.findAll();
    }

    @QueryHandler
    public ExperimentView handle(RetrieveIdExperimentQuery query) {
        return experimentViewRepository.findById(query.getId()).orElse(null);
    }

    @QueryHandler
    public ExperimentView handle(RetrieveClassifierModelExperimentQuery query) {
        return experimentViewRepository.findByClassifier_RemoteIdAndClassifier_ModelId(query.getClassifierId(), query.getModelId())
                .orElse(null);
    }

    private void writeResults(@NonNull ExperimentView experiment, @NonNull Map<String, Double> newResults) {
        var currentResultMap = experiment.getResults();
        newResults.forEach((key, value) -> {
            if (!currentResultMap.containsKey(key)) {
                currentResultMap.put(key, new ArrayList<>());
            }
            currentResultMap.get(key).add(value);
        });
    }
}
