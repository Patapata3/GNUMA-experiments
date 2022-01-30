package org.unibayreuth.gnumaexperiments.restapi.queries;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveAllExperimentsQuery;
import org.unibayreuth.gnumaexperiments.queries.experiments.RetrieveIdExperimentQuery;
import org.unibayreuth.gnumaexperiments.views.ExperimentView;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.axonframework.messaging.responsetypes.ResponseTypes.*;

@RestController
@RequestMapping(value = "api/v1/experiment")
public class ExperimentQueryController {

    private final QueryGateway queryGateway;

    @Autowired
    public ExperimentQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @CrossOrigin
    @GetMapping
    public CompletableFuture<List<ExperimentView>> getExperiments() {
        return queryGateway.query(new RetrieveAllExperimentsQuery(), multipleInstancesOf(ExperimentView.class));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ExperimentView>> getExperimentById(@PathVariable UUID id) {
        return queryGateway.query(new RetrieveIdExperimentQuery(id), instanceOf(ExperimentView.class))
                .thenApply(it -> it != null ? ResponseEntity.ok(it) : ResponseEntity.notFound().build());
    }
}
