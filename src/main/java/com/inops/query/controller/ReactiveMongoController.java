package com.inops.query.controller;


import com.inops.query.reactive.ReactiveMongoService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/query/attendance")
public class ReactiveMongoController {

    private final ReactiveMongoService reactiveMongoService;

    public ReactiveMongoController(ReactiveMongoService reactiveMongoService) {
        this.reactiveMongoService = reactiveMongoService;
    }

    // Fetch all documents from a collection
    @GetMapping("/{collection}")
    public Flux<Object> fetchAll(@PathVariable String collection) {
        return reactiveMongoService.findAll(collection);
    }

    // Fetch a document by ID
    @GetMapping("/{collection}/{id}")
    public Mono<ResponseEntity<Object>> fetchById(@PathVariable String collection, @PathVariable String id) {
        return reactiveMongoService.findById(collection, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Fetch documents with filters (query parameters)
    @GetMapping("/{collection}/search")
    public Flux<Object> fetchWithFilters(@PathVariable String collection, @RequestParam Map<String, String> filters) {
        Query query = new Query();
        filters.forEach((key, value) -> query.addCriteria(Criteria.where(key).is(value)));
        return reactiveMongoService.findWithFilters(collection, query);
    }

    // Create a document in a collection
    @PostMapping("/{collection}")
    public Mono<ResponseEntity<Object>> create(@PathVariable String collection, @RequestBody Object document) {
        return reactiveMongoService.create(collection, document)
                .map(savedDoc -> ResponseEntity.ok(savedDoc));
    }

    // Update a document in a collection by ID
    @PutMapping("/{collection}/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable String collection, @PathVariable String id, @RequestBody Object updatedDocument) {
        return reactiveMongoService.update(collection, id, updatedDocument)
                .map(updatedDoc -> ResponseEntity.ok(updatedDoc))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Delete a document by ID
    @DeleteMapping("/{collection}/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String collection, @PathVariable String id) {
        return reactiveMongoService.delete(collection, id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
