package com.rabo.demo.util;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;
import com.rabo.demo.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	public PersonRepository personRepo;
	
	public Iterable<Person> getPersonList() {
		System.out.println("Inside GET MApping");
		return personRepo.findAll();
	}
	
	public String addPerson(Person[] person) {
		System.out.println("Inside POST MApping" + person);
		try {
			for (Person per : person) {
				personRepo.save(per);
			}
			return "Record Saved Successfully";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Entered Record already available. Try saving Unique Name.";
		}

	}
	
	public Optional<Person> getById(int id) {
		return Optional.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

	}
	
	public String updateAddress(PersonAddress partialUpdate) {
		System.out.println("Inside Patch Handler");
		int id = partialUpdate.getPer_id();
		Optional<Person> person = Optional
				.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
		if (person.isPresent()) {
			Person per = person.get();
			per.setAddress(partialUpdate.getAddress());
			personRepo.save(per);
		}

		return "Updated Successfully";
	}
	
	public String deleteRecordById(int id) {
		personRepo.deleteById(id);
		return "Deleted Record Successfully";
	}
	
	public String deleteAllRecord() {
		personRepo.deleteAll();
		return "Deleted all record successfully";
	}
	
	public List<Person> getRecordByName(String name) {
		String firstname = name;
		String lastname = name;
		Optional<List<Person>> per = null;
		if (name.contains(" ")) {
			String[] names = name.split(" ");
			firstname = names[0];
			lastname = names[1];
			per = Optional.of(personRepo.findByLastnameAndFirstnameAllIgnoreCase(lastname, firstname)
					.orElseThrow(() -> new RecordNotFoundException()));
		} else {
			per = Optional.of(personRepo.findByLastnameOrFirstnameAllIgnoreCase(lastname, firstname)
					.orElseThrow(() -> new RecordNotFoundException()));
		}

		List<Person> personList = per.get();
		return personList;
	}
}
