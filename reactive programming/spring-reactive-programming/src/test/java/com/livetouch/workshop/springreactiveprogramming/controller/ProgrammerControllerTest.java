package com.livetouch.workshop.springreactiveprogramming.controller;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.ID;
import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.PATH;
import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@DirtiesContext
public class ProgrammerControllerTest {

    @Autowired
    private ProgrammerReactiveRepository programmerReactiveRepository;
    @Autowired
    private WebTestClient webTestClient;

    //    @Before
    public void setUp() {

        List<Programmer> programmers = List.of(
                new Programmer("Alexandre", Math.random() * 100),
                new Programmer("Augusto", Math.random() * 100),
                new Programmer("Jullinana", Math.random() * 100),
                new Programmer("Henrique", Math.random() * 100),
                new Programmer("ID5", "Lecheta", Math.random() * 100)
        );

        programmerReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(programmers))
                .flatMap(programmerReactiveRepository::save)
//                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void saveProgrammer() {

        Programmer p = new Programmer("1", "Henrique recontrarado", Math.random() * 100);

        webTestClient.post()
                .uri(PATH)
                .body(p, Programmer.class)
                .exchange()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Henrique recontrarado")
                .jsonPath("$.rating").isNumber();
    }


    @Test
    public void findByTest() {

        StepVerifier.create(programmerReactiveRepository.findById("5e3c4a0c174cc728fa16ef3a"))
                .expectSubscription()
                .expectNextMatches(pessoa -> pessoa.getName().equalsIgnoreCase("alexandre"))
                .verifyComplete();
    }

    @Test
    public void findByNameTest() {

        StepVerifier.create(programmerReactiveRepository.findByNameLike("a").log())
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void saveProgrammerTest() {

        Programmer newProgrammer = new Programmer("Nascimento", Math.random() * 100);
        Mono<Programmer> newP = programmerReactiveRepository.save(newProgrammer);

        StepVerifier.create(newP.log("Novo programador: "))
                .expectSubscription()
                .expectNextMatches(p -> nonNull(p.getId()) && p.getName().startsWith("X"))
                .expectComplete();

    }

    @Test
    public void mandarEmboraProgrammer() {

        webTestClient.delete()
                .uri(ID, "ID5")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Void.class);
    }

    @Test
    public void updateProgrammerTest() {

        String name = "Alexandre";
        Mono<Programmer> alexandreXimenes = programmerReactiveRepository
                .findByName(name)
                .map(n -> {
                    n.setName("Alexandre Ximenes");
                    return n;
                }).flatMap(newName -> programmerReactiveRepository.save(newName));

        StepVerifier.create(alexandreXimenes)
                .expectSubscription()
                .expectNextMatches(n -> n.getName().endsWith("s"))
                .verifyComplete();
    }

    @Test
    public void updateProgrammerEndpoint() {

        Programmer programmer = new Programmer("Ailton");

        Mono<Programmer> Ailton = programmerReactiveRepository.findById("ID5")
                .flatMap(p -> {
                    p.setName(programmer.getName());
                    return programmerReactiveRepository.save(p);
                });

        webTestClient.put()
                .uri(ID, "ID5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(Ailton), Programmer.class)
                .exchange()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Ailton");
    }

    @Test
    public void deleteProgrammer() {

        Mono<Void> Unemployed = programmerReactiveRepository.findById("ID5")
                .map(Programmer::getId)
                .flatMap(programmerReactiveRepository::deleteById);

        StepVerifier.create(Unemployed.log())
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void IncreaseSalarySortedProgrammer() {

        Optional<Programmer> sortudo = programmerReactiveRepository.findAll()
                .collectList()
                .block()
                .stream()
                .sorted(comparing(Programmer::getName))
                .findAny();
        sortudo.ifPresent((e) -> System.out.println(e + "ganhou aumento mesmo!"));

        List<Programmer> programmers = programmerReactiveRepository.findAll()
                .collectList()
                .block();

        Collections.shuffle(programmers);

        programmers.stream().findFirst().ifPresent((e) -> System.out.println(e + "ganhou aumento mesmo!"));
    }

    @Test
    public void findAllDBTest() {

        StepVerifier.create(programmerReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void findAll() {

        webTestClient.get()
                .uri(PATH)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Programmer.class)
                .hasSize(4)
                .consumeWith((response) -> {
                    List<Programmer> programmers = response.getResponseBody();
                    programmers.stream()
                            .filter(Objects::nonNull)
                            .map(Programmer::getName)
                            .collect(Collectors.toList())
                            .forEach(System.out::println);
                });
    }

    @Test
    public void findAll2() {

        Flux<Programmer> programmers = webTestClient.get()
                .uri(PATH)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .returnResult(Programmer.class)
                .getResponseBody();

        StepVerifier.create(programmers)
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void findByIdTest() {

        webTestClient.get()
                .uri(ID, "1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Programmer.class);
    }


    @Test
    public void findAllTest() {

        webTestClient.get()
                .uri(PATH)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Programmer.class)
                .hasSize(4);
    }

    @Test
    public void findRuntimeException() {

        webTestClient.get()
                .uri(PATH.concat("runtimeException"))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("Ocorreu um erro!");
    }
}
