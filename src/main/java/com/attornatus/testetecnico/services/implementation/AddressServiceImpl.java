package com.attornatus.testetecnico.services.implementation;

import com.attornatus.testetecnico.dtos.requests.AddressRequestDto;
import com.attornatus.testetecnico.dtos.requests.PersonAndAddressRequestDto;
import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import com.attornatus.testetecnico.repositories.AddressRepository;
import com.attornatus.testetecnico.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address create(AddressRequestDto dto, Person person) {
        Address address = new Address(dto, person);

        if (address.isMain()) {
            Optional<Address> responseMainAddress = this.addressRepository.findOneByPersonAndIsMain(person, true);

            if (responseMainAddress.isPresent()) {
                Address mainAddress = responseMainAddress.get();
                mainAddress.setMain(false);

                this.addressRepository.save(mainAddress);
            }
        }

        return this.addressRepository.saveAndFlush(address);
    }

    @Override
    public Address create(PersonAndAddressRequestDto dto, Person person) {
        return this.addressRepository.saveAndFlush(new Address(dto, person));
    }

    @Override
    public Address edit(AddressRequestDto dto, Address address) {
        return null;
    }

    @Override
    public Address editMainAddress(Person person, Long id) {
        return null;
    }

    @Override
    public Address getOne(Person person, Long id) {
        return null;
    }

    @Override
    public List<Address> getAll(Person person, int page) {
        return null;
    }

    @Override
    public void delete(Person person, Long id) {

    }
}
