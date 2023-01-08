package com.attornatus.testetecnico.integration;

import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PersonIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Test
    public void createPerson() throws Exception {
        String json = PersonIntegrationTests.asJson(PersonIntegrationTests.factoryPersonMap());

        this.mockMvc.perform(
                        post("/api/pessoas")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(PersonIntegrationTests.factoryPersonResponseAsJson()));
    }

    @Test
    public void getPerson() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());

        this.mockMvc.perform(get("/api/pessoas/" + person.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(PersonIntegrationTests.factoryPersonResponseAsJson()));
    }

    @Test
    public void getPeople() throws Exception {
        this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());

        this.mockMvc.perform(get("/api/pessoas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta").isNotEmpty())
                .andExpect(jsonPath("$.data[*]").isNotEmpty());
    }

    @Test
    public void editPerson() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());

        Map<String, Object> responseTest = PersonIntegrationTests.factoryPersonResponse();
        responseTest.put("nome", "Teste 2");
        responseTest.put("data_nascimento", "1999-01-01");

        Map<String, String> content = new HashMap<>();
        content.put("nome", "Teste 2");
        content.put("data_nascimento", "1999-01-01");

        this.mockMvc.perform(
                        put("/api/pessoas/" + person.getId())
                                .content(PersonIntegrationTests.asJson(content))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(PersonIntegrationTests.asJson(responseTest)));
    }

    @Test
    public void deletePerson() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());

        this.mockMvc.perform(delete("/api/pessoas/" + person.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private static Map<String, String> factoryPersonMap() {
        Map<String, String> data = new HashMap<>();

        data.put("nome", "Teste");
        data.put("data_nascimento", "2000-01-01");
        data.put("cidade_principal", "São Paulo");
        data.put("estado_principal", "São Paulo");
        data.put("cep_principal", "03800-000");
        data.put("logradouro_principal", "Rua teste");
        data.put("numero_principal", "123");

        return data;
    }

    public static PersonAndAddressRequestDto factoryPersonRequestDto() {
        PersonAndAddressRequestDto personAndAddressRequestDto = new PersonAndAddressRequestDto();

        personAndAddressRequestDto.nome = "Teste";
        personAndAddressRequestDto.data_nascimento = LocalDate.parse("2000-01-01");
        personAndAddressRequestDto.cidade_principal = "São Paulo";
        personAndAddressRequestDto.estado_principal = "São Paulo";
        personAndAddressRequestDto.logradouro_principal = "Rua teste";
        personAndAddressRequestDto.cep_principal = "03800-000";
        personAndAddressRequestDto.numero_principal = "123";

        return personAndAddressRequestDto;
    }

    public static Map<String, Object> factoryPersonResponse() {
        Map<String, String> address = new HashMap<>();

        address.put("cidade", "São Paulo");
        address.put("estado", "São Paulo");
        address.put("cep", "03800-000");
        address.put("logradouro", "Rua teste");
        address.put("numero", "123");

        Map<String, Object> data = new HashMap<>();

        data.put("nome", "Teste");
        data.put("data_nascimento", "2000-01-01");
        data.put("endereco_principal", address);

        return data;
    }

    public static String factoryPersonResponseAsJson() {
        return PersonIntegrationTests.asJson(PersonIntegrationTests.factoryPersonResponse());
    }

    private static String asJson(Object object) throws RuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
