package com.attornatus.testetecnico.unit;

import com.attornatus.testetecnico.dtos.requests.PersonRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
public class PersonTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createPersonTest() throws Exception {
        String json = PersonTests.asJson(PersonTests.factoryPersonDto());

        System.out.println(PersonTests.factoryPostResponse());

        this.mockMvc.perform(
                        post("/api/pessoas")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(PersonTests.factoryPostResponse()));
    }

    private static Map<String, String> factoryPersonDto() {
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

    private static String factoryPostResponse() {
        List<Map<String, String>> addresses = new ArrayList<>();
        Map<String, String> address = new HashMap<>();

        address.put("cidade_principal", "São Paulo");
        address.put("estado_principal", "São Paulo");
        address.put("cep_principal", "03800-000");
        address.put("logradouro_principal", "Rua teste");
        address.put("numero_principal", "123");

        addresses.add(address);

        Map<String, Object> data = new HashMap<>();

        data.put("nome", "Teste");
        data.put("data_nascimento", "2000-01-01");
        data.put("enderecos", addresses);

        return PersonTests.asJson(data);
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
