package com.attornatus.testetecnico.entities;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Address> addresses;

    public Person(PersonAndAddressRequestDto dto) {
        this.name = dto.nome;
        this.birthdate = dto.data_nascimento;
    }
}
