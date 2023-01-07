package com.attornatus.testetecnico.unit;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

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

    public static PersonRequestDto factoryPersonRequestDto() {
        PersonRequestDto personRequestDto = new PersonRequestDto();

        personRequestDto.nome = "Teste";
        personRequestDto.data_nascimento = LocalDate.parse("2000-01-01");

        return personRequestDto;
    }

    public static Person factoryPersonModel() {
        return new Person(PersonUnitTests.factoryPersonRequestDto());
    }
}
