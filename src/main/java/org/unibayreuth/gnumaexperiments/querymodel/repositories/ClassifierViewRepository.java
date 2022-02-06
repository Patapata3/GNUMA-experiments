package org.unibayreuth.gnumaexperiments.querymodel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import java.util.List;
import java.util.Optional;

public interface ClassifierViewRepository extends MongoRepository<ClassifierView, String> {
    @NonNull
    Optional<ClassifierView> findByAddress(@NonNull String address);
    @NonNull
    List<ClassifierView> findAllByAddressIn(@NonNull List<String> addresses);
}
