package com.attornatus.testetecnico.dtos.responses;

import com.attornatus.testetecnico.entities.Address;

public class AddressReponseDto {
    public String logradouro;
    public String cep;
    public String numero;
    public String cidade;
    public String estado;

    public AddressReponseDto(Address address) {
        this.cidade = address.getCity();
        this.estado = address.getState();
        this.logradouro = address.getStreet();
        this.numero = address.getNumber();
        this.cep = address.getZipCode();
    }
}