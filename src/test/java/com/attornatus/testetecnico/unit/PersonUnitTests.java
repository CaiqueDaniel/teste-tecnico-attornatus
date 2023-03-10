package com.attornatus.testetecnico.unit;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.dtos.responses.PersonResponseDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.exceptions.NotFoundException;
import com.attornatus.testetecnico.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
public class PersonUnitTests {
    @Autowired
    private PersonService personService;

    @Test
    public void getPerson() {
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());
        Person responsePerson = this.personService.getOne(person.getId());

        assertThat(responsePerson.getId()).isEqualTo(person.getId());
        assertThat(responsePerson.getName()).isEqualTo(person.getName());
        assertThat(responsePerson.getBirthdate()).isEqualTo(person.getBirthdate());
    }

    @Test
    public void getAllPersons() {
        PersonAndAddressRequestDto personAndAddressRequestDto = PersonUnitTests.factoryPersonRequestDto();

        this.personService.create(personAndAddressRequestDto);

        personAndAddressRequestDto.nome = "Teste 2";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("1970-01-01");

        this.personService.create(personAndAddressRequestDto);

        PaginationResponse<PersonResponseDto> responsePeople = this.personService.getAll(1);

        assertThat(responsePeople.data).isNotEmpty();
    }

    @Test
    public void createPerson() {
        Person testPerson = PersonUnitTests.factoryPersonModel();
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());

        assertThat(person.getName()).isEqualTo(testPerson.getName());
        assertThat(person.getBirthdate()).isEqualTo(testPerson.getBirthdate());
    }

    @Test
    public void editPerson() {
        PersonAndAddressRequestDto personAndAddressRequestDto = PersonUnitTests.factoryPersonRequestDto();
        Person person = this.personService.create(personAndAddressRequestDto);

        personAndAddressRequestDto.nome = "Teste 2";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("1999-01-01");

        person = this.personService.edit(personAndAddressRequestDto, person);

        assertThat(person.getName()).isEqualTo(personAndAddressRequestDto.nome);
        assertThat(person.getBirthdate()).isEqualTo(personAndAddressRequestDto.data_nascimento);
    }

    @Test
    public void deletePerson() {
        Person person = this.personService.create(PersonUnitTests.factoryPersonRequestDto());

        this.personService.delete(person);

        assertThatThrownBy(() -> this.personService.getOne(person.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Recurso n??o encontrado");
    }

    public static PersonAndAddressRequestDto factoryPersonRequestDto() {
        PersonAndAddressRequestDto personAndAddressRequestDto = new PersonAndAddressRequestDto();

        personAndAddressRequestDto.nome = "Teste";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("2000-01-01");
        personAndAddressRequestDto.cidade_principal = "S??o Paulo";
        personAndAddressRequestDto.estado_principal = "S??o Paulo";
        personAndAddressRequestDto.logradouro_principal = "Rua teste";
        personAndAddressRequestDto.cep_principal = "03808-000";
        personAndAddressRequestDto.numero_principal = "123";

        return personAndAddressRequestDto;
    }

    public static Person factoryPersonModel() {
        return new Person(PersonUnitTests.factoryPersonRequestDto());
    }
}
