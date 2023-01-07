package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Person;

import java.util.List;

public interface PersonService {
    Person create(PersonAndAddressRequestDto dto);

    Person edit(PersonRequestDto dto, Person person);

    Person getOne(Long id);

    List<Person> getAll(int page);
}
