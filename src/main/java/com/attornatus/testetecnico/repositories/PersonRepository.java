package com.attornatus.testetecnico.repositories;

import com.attornatus.testetecnico.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
