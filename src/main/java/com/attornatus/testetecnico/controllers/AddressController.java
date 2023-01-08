package com.attornatus.testetecnico.controllers;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.AddressResponseDto;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Endereços")
public class AddressController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/pessoas/{person_id}/enderecos")
    @Parameters({
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Content-Type",
                    schema = @Schema(type = "string", allowableValues = {"application/json"}, defaultValue = "application/json"),
                    required = true
            )
    })
    public ResponseEntity<AddressResponseDto> create(@RequestBody @Valid AddressRequestDto addressRequestDto, @PathVariable("person_id") Long personId) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.create(addressRequestDto, person);

        return new ResponseEntity<>(new AddressResponseDto(address), HttpStatus.CREATED);
    }

    @PutMapping("/pessoas/{person_id}/enderecos/{id}")
    @Parameters({
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Content-Type",
                    schema = @Schema(type = "string", allowableValues = {"application/json"}, defaultValue = "application/json"),
                    required = true
            )
    })
    public ResponseEntity<AddressResponseDto> edit(
            @RequestBody @Valid AddressRequestDto addressRequestDto,
            @PathVariable("person_id") Long personId,
            @PathVariable("id") Long id
    ) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.getOne(person, id);
        address = this.addressService.edit(addressRequestDto, address);

        return ResponseEntity.ok(new AddressResponseDto(address));
    }

    @PatchMapping("/pessoas/{person_id}/enderecos/{id}/endereco_principal")
    public ResponseEntity<AddressResponseDto> editMainAddress(@PathVariable("person_id") Long personId, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.getOne(person, id);
        address = this.addressService.setAsMainAddress(address);

        return ResponseEntity.ok(new AddressResponseDto(address));
    }

    @GetMapping("/pessoas/{person_id}/enderecos/{id}")
    public ResponseEntity<AddressResponseDto> getOne(@PathVariable("person_id") Long personId, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.getOne(person, id);

        return ResponseEntity.ok(new AddressResponseDto(address));
    }

    @GetMapping("/pessoas/{person_id}/enderecos")
    public ResponseEntity<PaginationResponse<AddressResponseDto>> getAll(
            @PathVariable("person_id") Long personId,
            @RequestParam(name = "pagina", defaultValue = "1") int page
    ) {
        Person person = this.personService.getOne(personId);
        PaginationResponse<AddressResponseDto> response = this.addressService.getAll(person, page);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/pessoas/{person_id}/enderecos/{id}")
    public ResponseEntity<Void> destroy(@PathVariable("person_id") Long personId, @PathVariable("id") Long id) {
        Person person = this.personService.getOne(personId);
        Address address = this.addressService.getOne(person, id);

        this.addressService.delete(address);

        return ResponseEntity.accepted().build();
    }
}
