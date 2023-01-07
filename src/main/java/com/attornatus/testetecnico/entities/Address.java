package com.attornatus.testetecnico.entities;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "addresses")
public class Address extends Model {
    @Setter
    @Column(nullable = false, length = 255)
    private String street;

    @Setter
    @Column(nullable = false, length = 9)
    private String zipCode;

    @Setter
    @Column(nullable = false, length = 6)
    private String number;

    @Setter
    @Column(nullable = false, length = 255)
    private String state;

    @Setter
    @Column(nullable = false, length = 255)
    private String city;

    @Setter
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Person person;

    public Address(PersonAndAddressRequestDto dto, Person person) {
        this.city = dto.cidade_principal;
        this.state = dto.estado_principal;
        this.street = dto.logradouro_principal;
        this.number = dto.numero_principal;
        this.zipCode = dto.cep_principal;
        this.person = person;
    }
}
