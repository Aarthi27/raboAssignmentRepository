package com.rabo.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;
import com.rabo.demo.service.PersonService;

@RestController
@RequestMapping("/persondetails")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	private static final String url="sdfsd";

//	@RequestMapping(value = "/getPersonList", method = RequestMethod.GET)
	@GetMapping(MappingURL.PERSON_GET_ALL)
	public ResponseEntity<List<Person>> getPersonList() {
		System.out.println("Inside GET MApping");
		return new ResponseEntity<List<Person>>(personService.getPersonList(), null,HttpStatus.OK);
	}

	@PostMapping(MappingURL.PERSON_ADD)
	public String addPerson(@RequestBody Person[] person) {
		String message = personService.addPerson(person);
		return message;
	}

	@GetMapping(MappingURL.PERSON_GET_BY_ID)
	public ResponseEntity<Person> getById(@PathVariable("id") int id) {
		Optional<Person> opPerson =personService.getById(id);
		return new ResponseEntity<Person>(opPerson.get(), null,HttpStatus.OK);
	}

	@PatchMapping(MappingURL.PERSON_UPDATE) // works with even @PutMapping
	public String updateAddress(@RequestBody PersonAddress partialUpdate) {
		String message = personService.updateAddress(partialUpdate);
		return message;
	}

	@DeleteMapping(MappingURL.PERSON_DELETE_BY_ID)
	public String deleteById(@PathVariable("id") int id) {
		return personService.deleteRecordById(id);
	}

	@DeleteMapping(MappingURL.PERSON_DELETE_ALL)
	public String deleteAllRecord() {
		return personService.deleteAllRecord();
	}

//	@GetMapping("/person/{searchName}")
//	public List<Person> getByName(@PathVariable("searchName") String name) {
//		List<Person> personList = personService.getRecordByName(name);
//		return personList;
//	}
	
	@GetMapping(MappingURL.PERSON_SEARCH_BY_NAME)
	public ResponseEntity<List<Person>> getByName(@RequestParam(name = "firstName") String firstname,
								  @RequestParam(name = "lastName") String lastname) {
		List<Person> personList = personService.getRecordByName(firstname, lastname);
		return new ResponseEntity<List<Person>>(personList, null,HttpStatus.OK);
	}
	
}
