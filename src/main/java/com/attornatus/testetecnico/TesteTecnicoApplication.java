package com.attornatus.testetecnico;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "API Teste Técnico Attornatus",
        description = "API para gerenciar pessoas.",
        version = "v1"
))
@SpringBootApplication
public class TesteTecnicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesteTecnicoApplication.class, args);
    }

}
