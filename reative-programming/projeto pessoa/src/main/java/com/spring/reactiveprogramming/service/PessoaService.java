package com.spring.reactiveprogramming.service;

import com.spring.reactiveprogramming.document.Pessoa;
import com.spring.reactiveprogramming.repository.PessoaReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private final PessoaReactiveRepository pessoaReactiveRepository;

    public Flux<Pessoa> findAll() {
        return pessoaReactiveRepository.findAll();
    }

    public Mono<Pessoa> findById(String id) {
        return pessoaReactiveRepository.findById(id);
    }

    public Mono<Pessoa> save(Pessoa pessoa) {
        return pessoaReactiveRepository.save(pessoa);
    }

    public Mono<Pessoa> update(String id, Pessoa pessoa) {
        return pessoaReactiveRepository
                .findById(id)
                .map(p -> {
                    p.setNome(pessoa.getNome());
                    p.setEmail(pessoa.getEmail());
                    return p;
                })
                .flatMap(pessoaReactiveRepository::save);
    }

    public Mono<Void> delete(String id) {
        return pessoaReactiveRepository
                .findById(id)
                .flatMap(pessoaReactiveRepository::delete);
    }
}
