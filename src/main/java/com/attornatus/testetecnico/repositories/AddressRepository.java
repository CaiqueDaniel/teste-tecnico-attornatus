package com.attornatus.testetecnico.repositories;

import com.attornatus.testetecnico.entities.Address;
import com.attornatus.testetecnico.entities.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByPerson(Person person, Pageable pageable);

    Optional<Address> findOneByPersonAndId(Person person, Long id);

    Optional<Address> findOneByPersonAndIsMain(Person person, boolean isMain);

    Long countByPerson(Person person);
}
