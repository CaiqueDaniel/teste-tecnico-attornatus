package com.attornatus.testetecnico.integration;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.services.AddressService;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AddressIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService personService;

    @Test
    public void createAddress() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());
        String json = AddressIntegrationTest.factoryAddressResponseAsJson();

        this.mockMvc.perform(
                        post("/api/pessoas/" + person.getId() + "/enderecos")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(json));
    }

    @Test
    public void editAddress() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());
        Address address = person.getAddresses().get(0);

        Map<String, String> addressMap = AddressIntegrationTest.factoryAddressResponse();
        addressMap.put("cidade", "Rio de Janeiro");
        addressMap.put("estado", "Rio de Janeiro");
        addressMap.put("cep", "20000-000");
        addressMap.put("logradouro", "Rua teste 2");
        addressMap.put("numero", "456");

        String json = AddressIntegrationTest.asJson(addressMap);

        this.mockMvc.perform(
                        put("/api/pessoas/" + person.getId() + "/enderecos/" + address.getId())
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void getAddress() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());
        Address address = this.addressService.create(AddressIntegrationTest.factoryAddressRequestDto(), person);

        this.mockMvc.perform(
                        get("/api/pessoas/" + person.getId() + "/enderecos/" + address.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(AddressIntegrationTest.factoryAddressResponseAsJson()));
    }

    @Test
    public void getAllAddressFromPerson() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());

        this.mockMvc.perform(
                        get("/api/pessoas/" + person.getId() + "/enderecos")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta").isNotEmpty())
                .andExpect(jsonPath("$.data[*]").isNotEmpty());
    }

    @Test
    public void deleteAddress() throws Exception {
        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());
        Address address = person.getAddresses().get(0);

        this.mockMvc.perform(delete("/api/pessoas/" + person.getId() + "/enderecos/" + address.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void defineMainAddress() throws Exception {
        AddressRequestDto addressRequestDto = AddressIntegrationTest.factoryAddressRequestDto();
        addressRequestDto.endereco_principal = false;

        Person person = this.personService.create(PersonIntegrationTests.factoryPersonRequestDto());
        Address address = this.addressService.create(addressRequestDto, person);

        this.mockMvc
                .perform(patch("/api/pessoas/" + person.getId() + "/enderecos/" + address.getId() + "/endereco_principal"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endereco_principal").value(true));
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

    public static Map<String, String> factoryAddressResponse() {
        Map<String, String> data = new HashMap<>();

        data.put("cidade", "São Paulo");
        data.put("estado", "São Paulo");
        data.put("cep", "03800-000");
        data.put("logradouro", "Rua Teste");
        data.put("numero", "123");

        return data;
    }

    public static String factoryAddressResponseAsJson() {
        return AddressIntegrationTest.asJson(AddressIntegrationTest.factoryAddressResponse());
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
