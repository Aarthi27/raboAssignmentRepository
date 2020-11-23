package com.rabo.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PersonRepository;
import com.rabo.demo.repository.PetRepository;
import com.rabo.demo.util.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	

//	@RequestMapping(value = "/getPersonList", method = RequestMethod.GET)
	@GetMapping("/getPersonList")
	public Iterable<Person> getPersonList() {
		System.out.println("Inside GET MApping");
		return personService.getPersonList();
	}

	@PostMapping("/addPerson")
	public String addPerson(@RequestBody Person[] person) {
		String message = personService.addPerson(person);
		return message;
	}

	@GetMapping("/getPersonById/{id}")
	public Optional<Person> getById(@PathVariable("id") int id) {
		return personService.getById(id);

	}

	@PatchMapping("/updateAddress") // works with even @PutMapping
	public String updateAddress(@RequestBody PersonAddress partialUpdate) {
		String message = personService.updateAddress(partialUpdate);
		return message;
	}

	@DeleteMapping("/deleteRecord/{id}")
	public String deleteById(@PathVariable("id") int id) {
		return personService.deleteRecordById(id);
	}

	@DeleteMapping("/deleteAll")
	public String deleteAllRecord() {
		return personService.deleteAllRecord();
	}

	@GetMapping("/findPersonByName/{searchName}")
	public List<Person> getByName(@PathVariable("searchName") String name) {
		List<Person> personList = personService.getRecordByName(name);
		return personList;
	}
	
}
