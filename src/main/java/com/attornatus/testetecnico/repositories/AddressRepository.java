package com.attornatus.testetecnico.repositories;

import com.attornatus.testetecnico.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
