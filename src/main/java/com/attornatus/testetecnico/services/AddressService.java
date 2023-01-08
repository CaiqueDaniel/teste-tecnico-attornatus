package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.AddressResponseDto;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;

import java.util.Optional;

public interface AddressService {
    Address create(AddressRequestDto dto, Person person);

    Address edit(AddressRequestDto dto, Address address);

    Address setAsMainAddress(Address address);

    Address getOne(Person person, Long id);

    Optional<Address> getMainAddress(Person person);

    PaginationResponse<AddressResponseDto> getAll(Person person, int page);

    void delete(Address address);
}
