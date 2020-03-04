package com.example.pessoas.service;

import com.example.pessoas.document.Programador;
import com.example.pessoas.error.CustoError;
import com.example.pessoas.repository.ProgramadoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProgramadoresHandler {

    public static final Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();
    private final ProgramadoresRepository programadoresRepository;

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(programadoresRepository.findAll(), Programador.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .body(programadoresRepository
                                .findById(id)
                                .onErrorMap((e) -> new CustoError("Erro inesperado"))
                        , Programador.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Programador> programadorMono = serverRequest.bodyToMono(Programador.class);
        return programadorMono
                .flatMap(p -> ServerResponse.ok()
                        .body(programadoresRepository.save(p), Programador.class))
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok()
                .body(programadoresRepository.deleteById(id), Programador.class)
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Programador> programador = serverRequest.bodyToMono(Programador.class);

        programador.flatMap(programmer -> {
            return programadoresRepository.findById(id)
                    .flatMap(p -> {
                        p.setNome(programmer.getNome());
                        p.setRating(Math.random() * 100);
                        return programadoresRepository.save(p);
                    });
        });

        return ServerResponse.ok()
                .body(programador, Programador.class)
                .switchIfEmpty(NOT_FOUND);
    }
}
