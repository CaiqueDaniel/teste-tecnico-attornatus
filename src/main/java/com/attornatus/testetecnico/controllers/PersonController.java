package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<PersonResponseDto> edit(@RequestBody @Valid PersonRequestDto personRequestDto, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);
        person = this.personService.edit(personRequestDto, person);

        Optional<Address> address = this.addressService.getMainAddress(person);

        return ResponseEntity.ok(new PersonResponseDto(person, address));
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<PersonResponseDto> getOne(@PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);
        Optional<Address> address = this.addressService.getMainAddress(person);

        return ResponseEntity.ok(new PersonResponseDto(person, address));
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PersonResponseDto>> getAll() {
        List<PersonResponseDto> personResponseDtos = this.personService
                .getAll(1)
                .stream()
                .map(person -> {
                    Optional<Address> address = this.addressService.getMainAddress(person);
                    return new PersonResponseDto(person, address);
                }).toList();

        return ResponseEntity.ok(personResponseDtos);
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Void> destroy(@PathVariable("id") Long id) {
        Person person = this.personService.getOne(id);
        this.personService.delete(person);

        return ResponseEntity.accepted().build();
    }
}
