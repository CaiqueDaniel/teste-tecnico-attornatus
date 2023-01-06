package com.attornatus.testetecnico.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class PersonRequestDto {
    @NotEmpty(message = "Nome é obrigatório.")
    @Size(max = 255, message = "Tamanho máximo do nome é 255 caracteres.")
    public String nome;

    @NotNull(message = "Data de nascimento é obrigatório.")
    public LocalDate data_nascimento;

    @NotEmpty(message = "Logradouro não pode ficar em branco.")
    @Size(max = 255,message = "Tamanho máximo do logradouro é 255 caracteres.")
    public String logradouro_principal;

    @NotEmpty(message = "CEP não pode ficar em branco.")
    @Size(max = 9,message = "Tamanho máximo do CEP é 9 caracteres.")
    public String cep_principal;

    @NotEmpty(message = "Número não pode ficar em branco.")
    @Size(max = 6,message = "Tamanho máximo do número é 6 caracteres.")
    public String numero_principal;

    @NotEmpty(message = "Cidade não pode ficar em branco.")
    @Size(max = 255,message = "Tamanho máximo da cidade é 255 caracteres.")
    public String cidade_principal;

    @NotEmpty(message = "Estado não pode ficar em branco.")
    @Size(max = 255,message = "Tamanho máximo do estado é 255 caracteres.")
    public String estado_principal;
}
