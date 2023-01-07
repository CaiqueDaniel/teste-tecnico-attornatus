package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.PersonService;
import com.attornatus.testetecnico.services.implementation.AddressServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressServiceImpl addressService;

    @PostMapping("/pessoas")
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonRequestDto dto) {
        Person person = this.personService.create(dto);
        Address address = this.addressService.create(dto, person);

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        return new ResponseEntity<PersonResponseDto>(new PersonResponseDto(person, addresses), HttpStatus.CREATED);
    }
}