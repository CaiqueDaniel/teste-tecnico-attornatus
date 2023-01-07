package com.attornatus.testetecnico.unit;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        List<Person> personTestList = new ArrayList<>();

        personTestList.add(this.personService.create(personAndAddressRequestDto));

        personAndAddressRequestDto.nome = "Teste 2";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("1970-01-01");

        personTestList.add(this.personService.create(personAndAddressRequestDto));

        List<Person> responsePeople = this.personService.getAll(1);

        assertThat(responsePeople).isNotEmpty();
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
        Person testPerson = PersonUnitTests.factoryPersonModel();

        testPerson.setName("Teste 2");
        testPerson.setBirthdate(LocalDate.parse("1999-01-01"));

        Person person = this.personService.edit(PersonUnitTests.factoryPersonRequestDto(), testPerson);

        assertThat(person.getName()).isEqualTo(testPerson.getName());
        assertThat(person.getBirthdate()).isEqualTo(testPerson.getBirthdate());
    }

    public static PersonAndAddressRequestDto factoryPersonRequestDto() {
        PersonAndAddressRequestDto personAndAddressRequestDto = new PersonAndAddressRequestDto();

        personAndAddressRequestDto.nome = "Teste";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("2000-01-01");

        return personAndAddressRequestDto;
    }

    public static Person factoryPersonModel() {
        return new Person(PersonUnitTests.factoryPersonRequestDto());
    }
}
