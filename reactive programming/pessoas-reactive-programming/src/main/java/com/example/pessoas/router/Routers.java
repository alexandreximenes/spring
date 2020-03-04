package com.example.pessoas.router;

import com.example.pessoas.service.ProgramadoresHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.example.pessoas.router.Endpoint.PATH;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Routers {

    @Bean
    public RouterFunction<ServerResponse> route(ProgramadoresHandler programadoresHandler) {
        return RouterFunctions
                .route(GET(PATH), programadoresHandler::all)
                .andRoute(GET(Endpoint.ID), programadoresHandler::findById)
                .andRoute(PUT(Endpoint.ID), programadoresHandler::update)
                .andRoute(POST(PATH), programadoresHandler::save)
                .andRoute(DELETE(Endpoint.ID), programadoresHandler::delete);
    }
}
