package com.rabo.demo.service;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PersonPetRepository;
import com.rabo.demo.repository.PetRepository;

@Service
//@Transactional
public class PetService {

	@Autowired
	public PetRepository petRepo;

	@Autowired
	public PersonPetRepository ppRepo;

	public Iterable<Pet> getPetList() {
		return petRepo.findAll();
	}

	public String addPet(Pet[] pets) {
		try {
			for (Pet pet : pets) {
				petRepo.save(pet);
			}
			return "Record Saved Successfully";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Arrays.toString(pets) + " Entered Record already available. Try saving Unique Name.";
		}
	}

	public String updatePet(Pet pet) {
		petRepo.save(pet);
		return "Record Updated Successfully";
	}

	public Optional<Pet> getById(int id) {
		return Optional.of(petRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

	}

	public String deleteAllRecord() {
		petRepo.deleteAll();
		return "All Records Deleted Successfully";
	}

	@Transactional
	public String deleteRecordById(int id) {
		int isMappingAvailable = ppRepo.existsByPetId(id);
		String result;
		System.out.println("----------------" + isMappingAvailable);
		if (isMappingAvailable > 0) {
			ppRepo.deleteByPetId(id);
		}
		Optional<Pet> pet = Optional.of(petRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
		if (pet.isPresent()) {
			petRepo.deleteById(id);
		}
		return "Deleted Record Successfully";
	}
}
