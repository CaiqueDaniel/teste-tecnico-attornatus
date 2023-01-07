package com.attornatus.testetecnico.services;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;

public interface AddressService {
    Address create(PersonAndAddressRequestDto dto, Person person);
}
