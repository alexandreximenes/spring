package com.example.pessoas.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class Programador {

    @Id
    @ToString.Exclude
    private String id;
    private String nome;
    @ToString.Exclude
    private Double rating;

    public Programador(String nome, Double rating) {
        this.nome = nome;
        this.rating = rating;
    }
}
