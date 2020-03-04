package com.livetouch.workshop.springreactiveprogramming.controller;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.errors.CustomError;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.ID;
import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.PATH;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(PATH)
public class ProgrammerController {

    private final ProgrammerReactiveRepository programmerReactiveRepository;

    @PostMapping
    public Mono<ResponseEntity> save(@RequestBody Programmer programmer) {

        return programmerReactiveRepository
                .save(programmer)
//                .timeout(Duration.ofMillis(1))
                .map(p -> new ResponseEntity(p, OK))
                .defaultIfEmpty(new ResponseEntity(BAD_REQUEST))
                .onErrorMap((e) -> new CustomError("Nem Jesus salva esse programador!"));
    }


    @GetMapping(ID)
    public Mono<ResponseEntity<Programmer>> getById(@PathVariable String id) {
        return programmerReactiveRepository.findById(id)
                .map(p -> new ResponseEntity<>(p, INTERNAL_SERVER_ERROR)) //zueira
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @DeleteMapping(ID)
    public Mono<Void> delete(@PathVariable String id) {
        return programmerReactiveRepository.findById(id)
                .flatMap(programmerReactiveRepository::delete);
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Programmer> findAll() {
        return programmerReactiveRepository
                .findAll()
                .delayElements(Duration.ofSeconds(1))
                .onErrorResume((e) -> {
                    System.out.println(e.getMessage());
                    return Flux.just(new Programmer());
                }).log();
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Mono<?>> findAllList() {
        Mono<List<Programmer>> map = programmerReactiveRepository
                .findAll()
                .collectList()
                .map(p -> p.stream().collect(Collectors.toList()));
        return new ResponseEntity<>(map, OK);
    }

    @PutMapping(ID)
    public Mono<ResponseEntity<Programmer>> update(@PathVariable String id, @RequestBody Programmer programmer) {
        return programmerReactiveRepository.findById(id)
                .flatMap(p -> {
                    p.setName(programmer.getName());
                    p.setRating(Math.random() * 100);
                    return programmerReactiveRepository.save(p);
                })
                .map(p -> new ResponseEntity<>(p, OK))
                .defaultIfEmpty(new ResponseEntity<>(BAD_REQUEST));
    }

    @GetMapping("/runtimeException")
    public Flux<Programmer> runtimeException() {

        return programmerReactiveRepository.findAll()
                .concatWith(Mono.error(new RuntimeException("Ocorreu um erro!")))
                .log();
    }

}
