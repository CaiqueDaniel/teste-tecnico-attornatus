package com.attornatus.testetecnico.integration;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PersonIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createPersonTest() throws Exception {
        String json = PersonIntegrationTests.asJson(PersonIntegrationTests.factoryPersonDto());

        this.mockMvc.perform(
                        post("/api/pessoas")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(PersonIntegrationTests.factoryPersonResponse()));
    }

    @Test
    public void getPersonTest() throws Exception {
        String json = PersonIntegrationTests.asJson(PersonIntegrationTests.factoryPersonDto());

        this.mockMvc.perform(
                post("/api/pessoas")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(get("/api/pessoas/1"))
                .andDo(print())
                .andExpect(content().json(PersonIntegrationTests.factoryPersonResponse()));
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

    private static String factoryPersonResponse() {
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

        return PersonIntegrationTests.asJson(data);
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
