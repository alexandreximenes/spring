package com.springboot.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

//@Entity(name = "USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

//    @Id
    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 100, message = "Digite entre 2 e 100 caracteres")
    private String name;
    @Past(message = "Digite uma data no passado")
    private LocalDate born;
}
