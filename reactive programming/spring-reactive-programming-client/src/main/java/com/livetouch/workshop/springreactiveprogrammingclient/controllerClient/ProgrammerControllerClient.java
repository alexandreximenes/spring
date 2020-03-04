package com.livetouch.workshop.springreactiveprogrammingclient.controllerClient;

import com.livetouch.workshop.springreactiveprogrammingclient.controllerClient.document.Programmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/")
public class ProgrammerControllerClient {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping("/findAll/client")
    public Flux<Programmer> findAllClient() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Programmer.class);
    }

    @GetMapping(value = "/findAll/client/exchange", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Programmer> findExchange() {
        return webClient.get()
                .uri("/")
                .exchange()
                .flatMapMany(response -> {
                    return response
                            .bodyToFlux(Programmer.class)
                            .delayElements(Duration.ofSeconds(1))
                            .log();
                });
    }

    @GetMapping(value = "/findId/client/retrieve/{id}")
    public Mono<Programmer> findByIdRetrieve(@PathVariable String id) {

        return webClient.get()
                .uri("/", id)
                .retrieve()
                .bodyToMono(Programmer.class)
                .log();
    }

    @GetMapping(value = "/findId/client/exchange")
    public Mono<Programmer> findByIdExchange() {

        String id = "5";
        return webClient.get()
                .uri("/", id)
                .exchange()
                .flatMap(response -> response.bodyToMono(Programmer.class))
                .log();
    }

    @PostMapping(value = "/save/client/retrieve")
    public Mono<Programmer> saveProgrammer(@RequestBody Programmer programmer) {

        Mono<Programmer> p = Mono.just(programmer);

        return webClient.post()
                .uri("/")
                .body(p, Programmer.class)
                .retrieve()
                .bodyToMono(Programmer.class)
                .log();
    }

    @PutMapping(value = "/update/client/retrieve/{id}")
    public Mono<Programmer> updateProgrammer(@PathVariable String id, @RequestBody Programmer programmer) {

        Mono<Programmer> p = Mono.just(programmer);

        return webClient.put()
                .uri("/", id)
                .body(p, Programmer.class)
                .retrieve()
                .bodyToMono(Programmer.class)
                .log();
    }

}
