package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServices {
    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(PersonRequestDto dto) {
        return this.personRepository.saveAndFlush(new Person(dto));
    }
}
