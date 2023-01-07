package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/pessoas")
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonAndAddressRequestDto dto) {
        Person person = this.personService.create(dto);

        return new ResponseEntity<>(new PersonResponseDto(person), HttpStatus.CREATED);
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Person> edit(@RequestBody PersonRequestDto personRequestDto, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);
        person = this.personService.edit(personRequestDto, person);

        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(this.personService.getAll(1));
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Void> destroy(@PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);
        this.personService.delete(person);

        return ResponseEntity.accepted().build();
    }
}
