package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.MetaData;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.exceptions.NotFoundException;
import com.attornatus.testetecnico.repositories.PersonRepository;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressService addressService;

    @Override
    public Person create(PersonAndAddressRequestDto dto) {
        Person person = new Person(dto);
        Address address = new Address(dto, person);

        person.setAddresses(List.of(address));

        return this.personRepository.saveAndFlush(person);
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
    public PaginationResponse<PersonResponseDto> getAll(int page) {
        page = page > 1 ? page : 1;

        Pageable pageable = PageRequest.of(page - 1, 30);
        Long total = this.personRepository.count();
        MetaData meta = new MetaData("/api/pessoas", page, 30, total);

        List<PersonResponseDto> personResponseDtos = this.personRepository
                .findAll(pageable)
                .map(person -> {
                    Optional<Address> address = this.addressService.getMainAddress(person);
                    return new PersonResponseDto(person, address);
                }).toList();

        return new PaginationResponse<>(meta, personResponseDtos);
    }

    @Override
    public void delete(Person person) {
        this.personRepository.delete(person);
    }
}
