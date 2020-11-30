package com.rabo.demo.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	public PetRepository petRepo;
	
	public Iterable<Pet> getPetList(){
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
			return Arrays.toString(pets)+" Entered Record already available. Try saving Unique Name.";
		}
	}
	
	
	public String updatePet(Pet pet) {
		petRepo.save(pet);
		return "Record Updated Successfully";
	}
	
	public String deleteAllRecord() {
		petRepo.deleteAll();
		return "All Records Deleted Successfully";
	}
	
   public String deleteRecordById(int id) {
		
		Optional<Pet> pet = Optional.of(petRepo.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));
		if(pet.isPresent()) {
		petRepo.deleteById(id);
		}
		return "Deleted Record Successfully";
	}
}
