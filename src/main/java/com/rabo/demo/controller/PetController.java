package com.rabo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.demo.model.Person;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PetRepository;

@RestController
@RequestMapping("/pet")
public class PetController {


	@Autowired
	private PetRepository petRepo;
	

	@GetMapping("/getPetList")
	public Iterable<Pet> getPetList(){
		return petRepo.findAll();
	}
	
	@PostMapping("/addPet")
	public String addPet(@RequestBody Pet[] pets) {
		try {
			for (Pet pet : pets) {
				petRepo.save(pet);
			}
			return "Record Saved Successfully";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Entered Record already available. Try saving Unique Name.";
		}
	}
	
	@PutMapping("/updatePet")
	public String updatePet(@RequestBody Pet pet) {
		petRepo.save(pet);
		return "Record Updated Successfully";
	}
	
	@DeleteMapping("/deleteAllPets")
	public String deleteAllRecord() {
		petRepo.deleteAll();
		return "All Records Deleted Successfully";
	}

}
