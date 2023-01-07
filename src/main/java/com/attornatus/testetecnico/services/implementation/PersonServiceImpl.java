package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.repositories.PersonRepository;
import com.attornatus.testetecnico.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person create(PersonRequestDto dto) {
        return this.personRepository.saveAndFlush(new Person(dto));
    }

    @Override
    public Person edit(PersonRequestDto dto, Person person) {
        return null;
    }

    @Override
    public Person getOne(Long id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }
}
