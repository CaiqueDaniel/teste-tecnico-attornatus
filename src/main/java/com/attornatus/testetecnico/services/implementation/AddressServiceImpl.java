package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.repositories.AddressRepository;
import com.attornatus.testetecnico.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address create(PersonRequestDto dto, Person person) {
        return this.addressRepository.saveAndFlush(new Address(dto, person));
    }
}
