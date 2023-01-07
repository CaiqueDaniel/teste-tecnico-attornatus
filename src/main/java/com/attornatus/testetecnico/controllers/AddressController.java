package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.AddressReponseDto;
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

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/pessoas/{person_id}/enderecos")
    public ResponseEntity<AddressReponseDto> create(@RequestBody @Valid AddressRequestDto addressRequestDto, @PathVariable("person_id") Long personId) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.create(addressRequestDto, person);

        return new ResponseEntity<>(new AddressReponseDto(address), HttpStatus.CREATED);
    }

    @GetMapping("/pessoas/{person_id}/enderecos/{id}")
    public ResponseEntity<AddressReponseDto> getOne(@PathVariable("person_id") Long personId, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.getOne(person, id);

        return ResponseEntity.ok(new AddressReponseDto(address));
    }

    @GetMapping("/pessoas/{person_id}/enderecos")
    public ResponseEntity<List<AddressReponseDto>> getAll(
            @PathVariable("person_id") Long personId,
            @RequestParam(name = "pagina", defaultValue = "1") int page
    ) {
        Person person = this.personService.getOne(personId);
        List<AddressReponseDto> addresses = this.addressService
                .getAll(person, page)
                .stream()
                .map(address -> new AddressReponseDto(address))
                .toList();

        return ResponseEntity.ok(addresses);
    }
}
