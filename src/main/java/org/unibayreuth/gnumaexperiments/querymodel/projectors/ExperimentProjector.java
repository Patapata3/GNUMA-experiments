package org.unibayreuth.gnumaexperiments.querymodel.projectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.dataModel.aggregate.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.events.experiments.CreatedExperimentEvent;
import org.unibayreuth.gnumaexperiments.events.experiments.DeletedExperimentEvent;
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
        ExperimentView view = new ExperimentView(event.getId(), event.getDate(), event.getClassifiers(),
                event.getTrainDatasetId(), event.getTestDatasetId());
        experimentViewRepository.save(view);
    }

    @EventHandler
    public void handle(DeletedExperimentEvent event) {
        experimentViewRepository.deleteById(event.getId());
    }

    @EventHandler
    public void handle(UpdatedExperimentEvent event) {
        experimentViewRepository.findById(event.getId()).ifPresent(experimentView -> {
            updateExperimentClassifier(experimentView, event);
            experimentViewRepository.save(experimentView);
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
        return experimentViewRepository.findByClassifiersRemoteIdAndClassifiersAddressAndClassifiersModelRemoteId(query.getClassifierId(),
                query.getAddress(), query.getModelId())
                .orElse(null);
    }

    private void updateExperimentClassifier(@NonNull ExperimentView experiment, @NonNull UpdatedExperimentEvent event) {
        experiment.getClassifiers()
                .stream()
                .filter(classifier -> classifier.getId().equals(event.getClassifierId()))
                .findAny()
                .ifPresent(classifier -> writeResults(classifier, event.getNewResults()));
    }

    private void writeResults(@NonNull ExperimentClassifier classifier, @NonNull Map<String, Double> newResults) {
        var currentResultMap = classifier.getResults();
        newResults.forEach((key, value) -> {
            if (!currentResultMap.containsKey(key)) {
                currentResultMap.put(key, new ArrayList<>());
            }
            currentResultMap.get(key).add(value);
        });
    }
}
