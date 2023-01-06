package com.attornatus.testetecnico.entities;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "persons")
public class Person extends Model {
    @Setter
    @Column(nullable = false, length = 255)
    private String name;

    @Setter
    @Column(nullable = false)
    private LocalDate birthdate;

    public Person(PersonRequestDto dto) {
        this.name = dto.nome;
        this.birthdate = dto.data_nascimento;
    }
}
