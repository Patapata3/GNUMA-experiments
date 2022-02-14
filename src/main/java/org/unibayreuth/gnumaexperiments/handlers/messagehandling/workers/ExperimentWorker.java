package org.unibayreuth.gnumaexperiments.handlers.messagehandling.workers;

import com.google.common.base.Objects;
import org.springframework.stereotype.Component;
import org.unibayreuth.gnumaexperiments.dataModel.entity.ExperimentClassifier;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import javax.annotation.Nullable;

@Component
public class ExperimentWorker {
    @Nullable
    public ExperimentClassifier getClassifierByAddress(ExperimentView experiment, String address) {
        return experiment.getClassifiers()
                .stream()
                .filter(classifier -> Objects.equal(classifier.getAddress(), address))
                .findAny()
                .orElse(null);
    }
}
