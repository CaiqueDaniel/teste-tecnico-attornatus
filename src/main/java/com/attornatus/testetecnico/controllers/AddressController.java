package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.AddressReponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/pessoas/{person_id}/enderecos")
    public ResponseEntity<AddressReponseDto> create(@RequestBody AddressRequestDto addressRequestDto, @PathVariable("person_id") Long personId) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.create(addressRequestDto, person);

        return new ResponseEntity<>(new AddressReponseDto(address), HttpStatus.CREATED);
    }
}
