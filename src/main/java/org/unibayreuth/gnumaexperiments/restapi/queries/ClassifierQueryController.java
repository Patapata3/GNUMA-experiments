package org.unibayreuth.gnumaexperiments.restapi.queries;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAddressClassifiersQuery;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveAllClassifiersQuery;
import org.unibayreuth.gnumaexperiments.queries.classifiers.RetrieveIdClassifierQuery;
import org.unibayreuth.gnumaexperiments.views.ClassifierView;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@RestController
@RequestMapping("/classifier")
public class ClassifierQueryController {

    private final QueryGateway queryGateway;

    @Autowired
    public ClassifierQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @CrossOrigin
    @GetMapping
    public CompletableFuture<List<ClassifierView>> getClassifiers() {
        return queryGateway.query(new RetrieveAllClassifiersQuery(), multipleInstancesOf(ClassifierView.class));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ClassifierView>> getClassifierById(@PathVariable String id) {
        return queryGateway.query(new RetrieveIdClassifierQuery(id), instanceOf(ClassifierView.class))
                .thenApply(it -> it != null ? ResponseEntity.ok(it) : ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @GetMapping("/byAddress/{address}")
    public CompletableFuture<List<ClassifierView>> getClassifiersByAddress(@PathVariable String address) {
        return queryGateway.query(new RetrieveAddressClassifiersQuery(address), multipleInstancesOf(ClassifierView.class));
    }
}
