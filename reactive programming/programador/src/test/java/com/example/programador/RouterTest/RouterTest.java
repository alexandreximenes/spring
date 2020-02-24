package com.example.programador.RouterTest;

import com.example.programador.document.Programador;
import com.example.programador.repository.ProgramadorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@DirtiesContext
public class RouterTest {

    @Autowired
    private ProgramadorRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testAll(){

        webTestClient.get()
                .uri("/")
                .exchange()
                .expectBodyList(Programador.class)
                .hasSize(3);
    }
}
