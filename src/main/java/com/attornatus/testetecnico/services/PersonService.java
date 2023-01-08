package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Person;

public interface PersonService {
    Person create(PersonAndAddressRequestDto dto);

    Person edit(PersonRequestDto dto, Person person);

    Person getOne(Long id);

    PaginationResponse<PersonResponseDto> getAll(int page);

    void delete(Person person);
}
