package com.attornatus.testetecnico.unit;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
import com.attornatus.testetecnico.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class AddressUnitTests {
    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService personService;

    @Test
    public void createAddress() {
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());
        Address addressTest = AddressUnitTests.factoryAddressModel(person);
        Address address = this.addressService.create(AddressUnitTests.factoryAddressRequestDto(), person);

        assertThat(address.getId()).isEqualTo(addressTest.getId());
        assertThat(address.getCity()).isEqualTo(addressTest.getCity());
        assertThat(address.getStreet()).isEqualTo(addressTest.getStreet());
        assertThat(address.getNumber()).isEqualTo(addressTest.getNumber());
        assertThat(address.getState()).isEqualTo(addressTest.getState());
        assertThat(address.getZipCode()).isEqualTo(addressTest.getZipCode());
        assertThat(address.isMain()).isEqualTo(addressTest.isMain());
    }

    @Test
    public void editAddress() {
        AddressRequestDto addressRequestDto = AddressUnitTests.factoryAddressRequestDto();
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());
        Address address = this.addressService.create(AddressUnitTests.factoryAddressRequestDto(), person);

        addressRequestDto.endereco_principal = false;
        addressRequestDto.cidade = "Rio de Janeiro";
        addressRequestDto.estado = "Rio de Janeiro";
        addressRequestDto.logradouro = "Rua teste 2";
        addressRequestDto.cep = "20000-000";
        addressRequestDto.numero = "4567";

        Address addressTest = AddressUnitTests.factoryAddressModel(person, addressRequestDto);

        address = this.addressService.edit(addressRequestDto, address);

        assertThat(address.getId()).isEqualTo(addressTest.getId());
        assertThat(address.getCity()).isEqualTo(addressTest.getCity());
        assertThat(address.getStreet()).isEqualTo(addressTest.getStreet());
        assertThat(address.getNumber()).isEqualTo(addressTest.getNumber());
        assertThat(address.getState()).isEqualTo(addressTest.getState());
        assertThat(address.getZipCode()).isEqualTo(addressTest.getZipCode());
        assertThat(address.isMain()).isEqualTo(addressTest.isMain());
    }

    @Test
    public void getAddress() {
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());
        Address address = this.addressService.create(AddressUnitTests.factoryAddressRequestDto(), person);
        Address responseAddress = this.addressService.getOne(person, address.getId());

        assertThat(address.getId()).isEqualTo(responseAddress.getId());
        assertThat(address.getCity()).isEqualTo(responseAddress.getCity());
        assertThat(address.getStreet()).isEqualTo(responseAddress.getStreet());
        assertThat(address.getNumber()).isEqualTo(responseAddress.getNumber());
        assertThat(address.getState()).isEqualTo(responseAddress.getState());
        assertThat(address.getZipCode()).isEqualTo(responseAddress.getZipCode());
        assertThat(address.isMain()).isEqualTo(responseAddress.isMain());
    }

    @Test
    public void getAllAddresses() {
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());
        AddressRequestDto addressRequestDto = AddressUnitTests.factoryAddressRequestDto();

        this.addressService.create(addressRequestDto, person);

        addressRequestDto.endereco_principal = false;
        addressRequestDto.cidade = "Rio de Janeiro";
        addressRequestDto.estado = "Rio de Janeiro";
        addressRequestDto.logradouro = "Rua teste 2";
        addressRequestDto.cep = "20000-000";
        addressRequestDto.numero = "4567";

        this.addressService.create(addressRequestDto, person);

        List<Address> addresses = this.addressService.getAll(person, 1);

        assertThat(addresses).isNotEmpty();
    }

    public static AddressRequestDto factoryAddressRequestDto() {
        AddressRequestDto addressRequestDto = new AddressRequestDto();

        addressRequestDto.cidade = "São Paulo";
        addressRequestDto.cep = "03800-000";
        addressRequestDto.numero = "123";
        addressRequestDto.estado = "São Paulo";
        addressRequestDto.logradouro = "Rua Teste";
        addressRequestDto.endereco_principal = true;

        return addressRequestDto;
    }

    public static Address factoryAddressModel(Person person) {
        return new Address(AddressUnitTests.factoryAddressRequestDto(), person);
    }

    public static Address factoryAddressModel(Person person, AddressRequestDto addressRequestDto) {
        return new Address(addressRequestDto, person);
    }
}
