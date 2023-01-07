package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;

import java.util.List;

public interface AddressService {
    Address create(AddressRequestDto dto, Person person);

    Address create(PersonAndAddressRequestDto dto, Person person);

    Address edit(AddressRequestDto dto, Address address);

    Address editMainAddress(Address address);

    Address getOne(Person person, Long id);

    List<Address> getAll(Person person, int page);

    void delete(Address address);
}
