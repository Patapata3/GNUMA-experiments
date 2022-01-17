package org.unibayreuth.gnumaexperiments.querymodel.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import java.util.List;

public interface ClassifierViewRepository extends MongoRepository<ClassifierView, String> {
    @NonNull
    List<ClassifierView> findAllByAddress(@NonNull String address);
}
