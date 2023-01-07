package com.attornatus.testetecnico.dtos.responses;

import com.attornatus.testetecnico.entities.Address;

public class AddressReponseDto {
    public Long id;
    public String logradouro;
    public String cep;
    public String numero;
    public String cidade;
    public String estado;

    public boolean endereco_principal;

    public AddressReponseDto(Address address) {
        this.id = address.getId();
        this.cidade = address.getCity();
        this.estado = address.getState();
        this.logradouro = address.getStreet();
        this.numero = address.getNumber();
        this.cep = address.getZipCode();
        this.endereco_principal = address.isMain();
    }
}
