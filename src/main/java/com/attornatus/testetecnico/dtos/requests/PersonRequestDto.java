package com.attornatus.testetecnico.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class PersonRequestDto {
    @NotEmpty(message = "Nome é obrigatório.")
    @Size(max = 255, message = "Tamanho máximo do nome é 255 caracteres.")
    public String nome;

    @NotNull(message = "Data de nascimento é obrigatório.")
    @PastOrPresent(message = "Data de nascimento inválida.")
    public LocalDate data_nascimento;
}
