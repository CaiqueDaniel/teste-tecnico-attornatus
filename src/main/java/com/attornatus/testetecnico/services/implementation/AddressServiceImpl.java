package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.responses.AddressResponseDto;
import com.attornatus.testetecnico.dtos.responses.MetaData;
import com.attornatus.testetecnico.dtos.responses.PaginationResponse;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.exceptions.NotFoundException;
import com.attornatus.testetecnico.repositories.AddressRepository;
import com.attornatus.testetecnico.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private static final int RESPONSE_LIMIT = 30;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address create(AddressRequestDto dto, Person person) {
        Address address = new Address(dto, person);

        this.handleMainAddress(address);

        return this.addressRepository.saveAndFlush(address);
    }

    @Override
    public Address edit(AddressRequestDto dto, Address address) {
        address.setStreet(dto.logradouro);
        address.setNumber(dto.numero);
        address.setZipCode(dto.cep);
        address.setCity(dto.cidade);
        address.setState(dto.estado);
        address.setMain(dto.endereco_principal);

        this.handleMainAddress(address);

        return this.addressRepository.saveAndFlush(address);
    }

    @Override
    public Address setAsMainAddress(Address address) {
        address.setMain(true);

        this.handleMainAddress(address);

        return this.addressRepository.saveAndFlush(address);
    }

    @Override
    public Address getOne(Person person, Long id) {
        Optional<Address> response = this.addressRepository.findOneByPersonAndId(person, id);

        if (response.isEmpty())
            throw new NotFoundException();

        return response.get();
    }

    @Override
    public Optional<Address> getMainAddress(Person person) {
        return this.addressRepository.findOneByPersonAndIsMain(person, true);
    }

    @Override
    public PaginationResponse<AddressResponseDto> getAll(Person person, int page) {
        page = page > 1 ? page : 1;

        Pageable pageable = PageRequest.of(page - 1, AddressServiceImpl.RESPONSE_LIMIT);
        Long total = this.addressRepository.countByPerson(person);
        MetaData meta = new MetaData(
                "/api/pessoas/" + person.getId() + "/enderecos",
                page,
                AddressServiceImpl.RESPONSE_LIMIT,
                total
        );

        List<AddressResponseDto> addressResponseDtos = this.addressRepository
                .findAllByPerson(person, pageable)
                .stream()
                .map(address -> new AddressResponseDto(address))
                .toList();

        return new PaginationResponse<>(meta, addressResponseDtos);
    }

    @Override
    public void delete(Address address) {
        this.addressRepository.delete(address);
    }

    private void handleMainAddress(Address address) {
        if (!address.isMain())
            return;

        Optional<Address> responseMainAddress = this.addressRepository.findOneByPersonAndIsMain(address.getPerson(), true);

        if (responseMainAddress.isPresent()) {
            Address mainAddress = responseMainAddress.get();
            mainAddress.setMain(false);

            this.addressRepository.save(mainAddress);
        }
    }
}
