package com.rabo.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.Pet;
import com.rabo.demo.service.PetService;

@RestController
@RequestMapping("/petdetails")
public class PetController {


	@Autowired
	private PetService petService;
	

	@GetMapping(MappingURL.PET_GET_ALL)
	public Iterable<Pet> getPetList(){
		return petService.getPetList();
	}
	
	@PostMapping(MappingURL.PET_ADD)
	public String addPet(@RequestBody Map<String,Object> map) {
		Pet pet = new Pet();
		pet = getPetObj(map);
		return petService.addPet(new Pet[] {pet});
	}
	
	@PutMapping(MappingURL.PET_UPDATE)
	public String updatePet(@RequestBody Map<String,Object> map) {
		Pet pet = getPetObj(map);
		pet.setPet_id(Integer.valueOf(map.get("pet_id").toString()));
		String message = petService.updatePet(pet);
		return message;
	}
	
	@DeleteMapping(MappingURL.PET_DELETE_ALL)
	public String deleteAllRecord() {
		String message = petService.deleteAllRecord();
		return message;
	}
	
	@GetMapping(MappingURL.PET_DELETE_BY_ID)
	public String deleteById(@PathVariable("id") int id) {
		return petService.deleteRecordById(id);

	}
	
	public Pet getPetObj(Map<String,Object> map) {
		Pet pet = new Pet();
		pet.setAge(Integer.valueOf(map.get("age").toString()));
		pet.setName(map.get("name").toString());
		
		ResponseEntity<Person> responsePerson = new RestTemplate().exchange("http://localhost:8080/persondetails"+MappingURL.PERSON_GET_BY_ID,HttpMethod.GET,null,new ParameterizedTypeReference<Person>() {
		}, map.get("person_id").toString());
		
		pet.setPerson(responsePerson.getBody());
		
		return pet;
	}

}
