package com.spring.reactiveprogramming.controller;

import com.spring.reactiveprogramming.constants.EndPoints;
import com.spring.reactiveprogramming.document.Pessoa;
import com.spring.reactiveprogramming.repository.PessoaReactiveRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@AutoConfigureWebTestClient
public class PessoaControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PessoaReactiveRepository pessoaReactiveRepository;

    List<Pessoa> pessoas = List.of(new Pessoa("Alexandre", "xyymenes@gmail.com"),
            new Pessoa("Augusto", "augusto@gmail.com"),
            new Pessoa("Lecheta", "lecheta@gmail.com"),
            new Pessoa("Julliana", "julliana@gmail.com"),
            new Pessoa("Lucas", "lucas@gmail.com"));

    @Before
    public void setUp() {

        System.out.println("PessoaControllerTest");
        pessoaReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(pessoas))
                .flatMap(pessoaReactiveRepository::save)
                .doOnNext(p -> System.out.println("Insert pessoa: " + p.getNome()))
                .blockLast();
    }

    @Test
    public void findAll() {

        webTestClient.get()
                .uri(EndPoints.PESSOAS.concat(EndPoints.ALL))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Pessoa.class)
                .hasSize(5);
    }
}
