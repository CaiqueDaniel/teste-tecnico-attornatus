package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.PersonService;
import com.attornatus.testetecnico.services.implementation.AddressServiceImpl;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonAndAddressRequestDto dto) {
        Person person = this.personService.create(dto);
        Address address = this.addressService.create(dto, person);

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        return new ResponseEntity<PersonResponseDto>(new PersonResponseDto(person, addresses), HttpStatus.CREATED);
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Person> edit(@RequestBody PersonRequestDto personRequestDto, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);

        return new ResponseEntity<Person>(this.personService.edit(personRequestDto, person), HttpStatus.OK);
    }
}
