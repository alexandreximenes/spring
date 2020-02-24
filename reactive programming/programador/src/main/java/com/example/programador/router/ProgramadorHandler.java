package com.example.programador.router;

import com.example.programador.document.Programador;
import com.example.programador.repository.ProgramadorRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ProgramadorHandler {

    Class<Programador> programador = Programador.class;

    @Autowired
    private ProgramadorRepository programadorRepository;

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(programadorRepository.findAll(), programador);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(programadorRepository.findById(id), programador);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(programador)
                .flatMap(p -> ServerResponse.ok()
                        .body(programadorRepository.save(p), programador));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Programador> programadorMonoRequest = serverRequest.bodyToMono(programador);
        programadorMonoRequest.flatMap(programadorMono -> {
            return programadorRepository
                    //find
                    .findById(id)
                    .flatMap(programadorRetrieved -> {
                        //update
                        programadorRetrieved.setName(programadorMono.getName());
                        programadorRetrieved.setRating(programadorMono.getRating());
                        return programadorRepository.save(programadorRetrieved);
                    });
        });

        return ServerResponse.ok()
                .body(programadorMonoRequest, programador)
                .defaultIfEmpty(ServerResponse.notFound().build().block());
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .body(programadorRepository.findById(id), programador)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
