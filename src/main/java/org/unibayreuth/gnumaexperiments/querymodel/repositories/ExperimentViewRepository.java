package org.unibayreuth.gnumaexperiments.querymodel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.util.Optional;
import java.util.UUID;

public interface ExperimentViewRepository extends MongoRepository<ExperimentView, UUID> {
    Optional<ExperimentView> findByClassifier_RemoteIdAndClassifier_ModelId(@NonNull String classifierRemoteId, @NonNull UUID modelId);
}
