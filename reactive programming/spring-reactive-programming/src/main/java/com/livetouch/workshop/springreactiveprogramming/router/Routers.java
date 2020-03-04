package com.livetouch.workshop.springreactiveprogramming.router;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.ID_V2;
import static com.livetouch.workshop.springreactiveprogramming.Constants.Endpoint.PATH_V2;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Routers {

    private final ProgrammerReactiveRepository programmerReactiveRepository;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(GET(PATH_V2), serverRequest -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(programmerReactiveRepository.findAll(), Programmer.class);
        })

                .andRoute(GET(PATH_V2), serverRequest -> {
                    String id = serverRequest.pathVariable("id");
                    return programmerReactiveRepository.findById(id)
                            .flatMap(p -> ServerResponse.ok()
                                    .body(fromObject(p)))
                            .switchIfEmpty(ServerResponse.notFound().build());

                })

                .andRoute(POST(PATH_V2), serverRequest -> {

                    return serverRequest.bodyToMono(Programmer.class)
                            .flatMap(p -> {
                                return ServerResponse.ok()
                                        .body(programmerReactiveRepository.save(p), Programmer.class)
                                        .switchIfEmpty(ServerResponse.badRequest().build());
                            });
                })

                .andRoute(DELETE(ID_V2), serverRequest -> {
                    String id = serverRequest.pathVariable("id");

                    return ServerResponse.ok()
                            .body(programmerReactiveRepository.deleteById(id), Programmer.class)
                            .switchIfEmpty(ServerResponse.badRequest().build());
                })

                .andRoute(PUT(ID_V2), serverRequest -> {
                    String id = serverRequest.pathVariable("id");
                    Mono<Programmer> programmerMono = serverRequest.bodyToMono(Programmer.class)
                            .flatMap(programmer -> {
                                return programmerReactiveRepository.findById(id)
                                        .flatMap(p -> {
                                            p.setName(programmer.getName());
                                            return programmerReactiveRepository.save(p);
                                        });
                            });
                    return ServerResponse.ok()
                            .body(programmerMono, Programmer.class)
                            .switchIfEmpty(ServerResponse.badRequest().build());
                });
    }
}
