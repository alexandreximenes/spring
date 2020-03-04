package com.example.pessoas.repository;

import com.example.pessoas.document.Programador;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProgramadoresRepository extends ReactiveMongoRepository<Programador, String> {
}
