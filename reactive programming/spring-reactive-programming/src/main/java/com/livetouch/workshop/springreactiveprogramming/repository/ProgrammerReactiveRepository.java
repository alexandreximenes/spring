package com.livetouch.workshop.springreactiveprogramming.repository;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProgrammerReactiveRepository extends ReactiveMongoRepository<Programmer, String> {
    Mono<Programmer> findByName(String name);

    Flux<Programmer> findByNameLike(String name);
}
