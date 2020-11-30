package com.rabo.demo.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabo.demo.constants.ResponseMessage;
import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PersonRepository;
import com.rabo.demo.repository.PetRepository;

@Service
public class PersonService {

	@Autowired
	public PersonRepository personRepo;
	

	
	public List<Person> getPersonList() {
		System.out.println("Inside GET MApping");
		return (List<Person>) personRepo.findAll();
	}
	
	public String addPerson(Person[] person) {
		System.out.println("Inside POST MApping" + person);
		try {
			for (Person per : person) {
				personRepo.save(per);
			}
			return ResponseMessage.ADDED;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseMessage.ADD_FAILED;
		}

	}
	
	public Optional<Person> getById(int id) {
		return Optional.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

	}
	
	public String updateAddress(PersonAddress partialUpdate) {
		System.out.println("Inside Patch Handler");
		int id = partialUpdate.getId();
		Optional<Person> person = Optional
				.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
		if (person.isPresent()) {
			Person per = person.get();
			per.setAddress(partialUpdate.getAddress());
			personRepo.save(per);
		}

		return ResponseMessage.UPDATED;
	}
	
	public String deleteRecordById(int id) {
		String result ;
		Optional<Person> per = getById(id);
		if(per.isPresent()) {
		personRepo.deleteById(id);
		result = ResponseMessage.DELETED;
		}else
		{
			result = ResponseMessage.DELETE_FAILED;
		}
		return result;
	}
	
	public String deleteAllRecord() {
		personRepo.deleteAll();
		return ResponseMessage.DELETE_ALL_SUCCESS;
	}
	
	public List<Person> getRecordByName(String firstname, String lastname) {
		
		Optional<List<Person>> per = null;
		if ((!StringUtils.isEmpty(firstname) && !StringUtils.isEmpty(lastname))) {
			
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
