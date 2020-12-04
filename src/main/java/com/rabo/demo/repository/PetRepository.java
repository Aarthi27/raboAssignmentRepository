package com.rabo.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabo.demo.model.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, Integer> {

}
