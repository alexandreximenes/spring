package com.livetouch.workshop.springreactiveprogramming.router;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ProgrammerHandler {

    @Autowired
    private ProgrammerReactiveRepository programmerReactiveRepository;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(programmerReactiveRepository.findAll(), Programmer.class);
    }
}
