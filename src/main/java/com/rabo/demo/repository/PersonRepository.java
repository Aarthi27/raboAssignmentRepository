package com.rabo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabo.demo.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

	public Optional<List<Person>> findByLastnameOrFirstnameAllIgnoreCase(String lastname, String firstname);
	
	public Optional<List<Person>> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
}
