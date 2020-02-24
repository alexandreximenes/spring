package com.example.programador.repository;

import com.example.programador.document.Programador;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProgramadorRepository extends ReactiveMongoRepository<Programador, String> {
}
