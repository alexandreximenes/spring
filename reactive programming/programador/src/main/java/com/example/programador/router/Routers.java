package com.example.programador.router;

import com.example.programador.document.Programador;
import com.example.programador.repository.ProgramadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Routers {
    //Parecido com Node e Laravel
    //Route::get("/", Controller@metodo);
    @Autowired
    private ProgramadorRepository repository;

    @Bean
    public RouterFunction<ServerResponse> router(ProgramadorHandler handler) {

        return RouterFunctions.route(
                GET("/").and(accept(MediaType.APPLICATION_JSON)), handler::all)
                .andRoute(GET("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(PUT("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
    }
}
