package org.unibayreuth.gnumaexperiments.querymodel.projectors;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;
import org.unibayreuth.gnumaexperiments.events.classifiers.CreatedClassifierEvent;
import org.unibayreuth.gnumaexperiments.events.classifiers.DeletedClassifierEvent;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAddressClassifiersQuery;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAllClassifiersQuery;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveIdClassifierQuery;
import org.unibayreuth.gnumaexperiments.querymodel.repositories.ClassifierViewRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ClassifierProjector {
    @Autowired
    private ClassifierViewRepository classifierViewRepository;

    @EventHandler
    public void handle(CreatedClassifierEvent event) {
        ClassifierView view = new ClassifierView(event.getId(), event.getAddress(), event.getHyperParameters());
        classifierViewRepository.save(view);
    }

    @EventHandler
    public void handle(DeletedClassifierEvent event) {
        classifierViewRepository.deleteById(event.getId());
    }

    @QueryHandler
    public List<ClassifierView> handle(RetrieveAllClassifiersQuery query) {
        return classifierViewRepository.findAll();
    }

    @QueryHandler
    public ClassifierView handle(RetrieveIdClassifierQuery query) {
        return classifierViewRepository.findById(query.getId()).orElse(null);
    }

    @QueryHandler
    public List<ClassifierView> handle(RetrieveAddressClassifiersQuery query) {
        return classifierViewRepository.findAllByAddress(query.getAddress());
    }
}
