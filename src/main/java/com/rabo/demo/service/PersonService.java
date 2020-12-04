package com.rabo.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.constants.ResponseMessage;
import com.rabo.demo.exception.RecordNotFoundException;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;
import com.rabo.demo.model.PersonPetMapping;
import com.rabo.demo.model.Pet;
import com.rabo.demo.repository.PersonPetRepository;
import com.rabo.demo.repository.PersonRepository;
import com.rabo.demo.repository.PetRepository;

@Service
//@Transactional
public class PersonService {

	@Autowired
	public PersonRepository personRepo;

	@Autowired
	public PersonPetRepository ppRepo;

	public List<Person> getPersonList() {
		System.out.println("Inside GET MApping");
		List<Person> per = (List<Person>) personRepo.findAll();
		for (Person p : per) {
			p.getPersonPetLinkList().stream().map(pp -> pp.getPet()).forEach(pet -> p.getPetList().add(pet));
//			for(PersonPetMapping pp : p.getPersonPetLinkList()) {
//				p.getPetList().add(pp.getPet());
//			}
		}

		return per;
	}

	public String addPerson(Person[] person) {
		System.out.println("Inside POST MApping---" + person);
		try {
			personRepo.saveAll(Arrays.asList(person));
//			for (Person per : person) {
//				personRepo.save(per);
//				personRepo.
//			}
			return ResponseMessage.ADDED;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseMessage.ADD_FAILED;
		}

	}

	public Optional<Person> getById(int id) {
		Optional<Person> per = Optional.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
		if (per.isPresent()) {
			for (PersonPetMapping pp : per.get().getPersonPetLinkList()) {
				per.get().getPetList().add(pp.getPet());
			}

		}
		return per;

	}

	public String updateAddress(PersonAddress partialUpdate) {
		System.out.println("Inside Patch Handler");
		int id = partialUpdate.getId();
		Optional<Person> person = Optional
				.of(personRepo.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
		if (person.isPresent()) {
			Person per = person.get();
			per.getPersonPetLinkList().stream().map(pp -> pp.getPet()).forEach(pet -> per.getPetList().add(pet));
			per.setAddress(partialUpdate.getAddress());
			personRepo.save(per);
		}

		return ResponseMessage.UPDATED;
	}

	@Transactional
	public String deleteRecordById(int id) {
		Boolean isMappingAvailable = ppRepo.existsByPersonId(id);
		String result;
		System.out.println("----------------" + isMappingAvailable);
		if (isMappingAvailable) {
			ppRepo.deleteByPersonId(id);
		}
		Optional<Person> per = getById(id);
		if (per.isPresent()) {
			personRepo.deleteById(id);
			result = ResponseMessage.DELETED;
		} else {
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

		for (Person p : personList) {
			for (PersonPetMapping pp : p.getPersonPetLinkList()) {
				p.getPetList().add(pp.getPet());
			}
		}
		return personList;
	}

	public String linkPersonToPet(Map<String, Object> map) {
		PersonPetMapping ppmap = new PersonPetMapping();

		ResponseEntity<Person> responsePerson = new RestTemplate().exchange(
				"http://localhost:8080/persondetails" + MappingURL.PERSON_GET_BY_ID, HttpMethod.GET, null,
				new ParameterizedTypeReference<Person>() {
				}, map.get("person_id").toString());

		ResponseEntity<Pet> responsePet = new RestTemplate().exchange(
				"http://localhost:8080/petdetails" + MappingURL.PET_GET_BY_ID, HttpMethod.GET, null,
				new ParameterizedTypeReference<Pet>() {
				}, map.get("pet_id").toString());

		ppmap.setPerson(responsePerson.getBody());
		ppmap.setPet(responsePet.getBody());

		ppRepo.save(ppmap);

		return "Person Mapped to Pet Successfully";

	}
}
