package com.attornatus.testetecnico.dtos.responses;

import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonResponseDto {
    public Long id;
    public String nome;
    public String data_nascimento;
    public String criado_em;
    public List<AddressReponseDto> enderecos;

    public PersonResponseDto(Person person, List<Address> addresses) {
        this.id = person.getId();
        this.nome = person.getName();
        this.data_nascimento = person.getBirthdate().toString();
        this.criado_em = person.getCreatedAt().toString();
        this.enderecos = addresses.stream().map(address -> new AddressReponseDto(address)).collect(Collectors.toList());
    }
}
