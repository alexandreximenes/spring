package com.spring.reactiveprogramming.repository;

import com.spring.reactiveprogramming.document.Pessoa;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PessoaReactiveRepository extends ReactiveMongoRepository<Pessoa, String> {
}
