package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.exceptions.NotFoundException;
import com.attornatus.testetecnico.repositories.PersonRepository;
import com.attornatus.testetecnico.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person create(PersonAndAddressRequestDto dto) {
        return this.personRepository.saveAndFlush(new Person(dto));
    }

    @Override
    public Person edit(PersonRequestDto dto, Person person) {
        person.setName(dto.nome);
        person.setBirthdate(dto.data_nascimento);

        return this.personRepository.saveAndFlush(person);
    }

    @Override
    public Person getOne(Long id) {
        Optional<Person> response = this.personRepository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException();

        return response.get();
    }

    @Override
    public List<Person> getAll() {
        return null;
    }
}
