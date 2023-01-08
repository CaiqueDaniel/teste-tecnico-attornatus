package com.attornatus.testetecnico.dtos.responses;

import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;

import java.util.Optional;

public class PersonResponseDto {
    public Long id;
    public String nome;
    public String data_nascimento;
    public String criado_em;
    public Optional<AddressResponseDto> endereco_principal;

    public PersonResponseDto(Person person) {
        this.id = person.getId();
        this.nome = person.getName();
        this.data_nascimento = person.getBirthdate().toString();
        this.criado_em = person.getCreatedAt().toString();
        this.endereco_principal = Optional.of(new AddressResponseDto(person.getAddresses().get(0)));
    }

    public PersonResponseDto(Person person, Optional<Address> address) {
        this.id = person.getId();
        this.nome = person.getName();
        this.data_nascimento = person.getBirthdate().toString();
        this.criado_em = person.getCreatedAt().toString();
        this.endereco_principal = address.map(AddressResponseDto::new);
    }
}
