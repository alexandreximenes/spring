package com.livetouch.workshop.springreactiveprogramming.RouterTest;

import com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint;
import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import org.junit.Assert;
import org.junit.Before;
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

import java.util.List;

import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.PATH_V2;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@DirtiesContext
public class RouterTest {

    @Autowired
    private ProgrammerReactiveRepository programmerReactiveRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void initData() {
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
                .doOnNext(System.out::println);
    }

    @Test
    public void findAll() {

        webTestClient.get()
                .uri(PATH_V2)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk()
                .expectBodyList(Programmer.class)
                .hasSize(5);

    }

    public void findById() {
    }

    public void delete() {
    }

    public void save() {
    }

    public void update() {
    }


}
