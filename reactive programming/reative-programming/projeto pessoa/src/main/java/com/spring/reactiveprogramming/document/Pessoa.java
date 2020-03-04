package com.spring.reactiveprogramming.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    private String id;
    private String nome;
    private String email;

    public Pessoa(Pessoa p) {
        this.nome = p.getNome();
        this.email = p.getEmail();
    }

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
