package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PersonController {
    @Autowired
    private PersonServices personServices;

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonRequestDto dto) {
        Person person = this.personServices.createPerson(dto);
        Address address = this.addressService.create(dto, person);

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        return new ResponseEntity<PersonResponseDto>(new PersonResponseDto(person, addresses), HttpStatus.CREATED);
    }
}
