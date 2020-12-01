package com.rabo.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.constants.ResponseMessage;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.Pet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PetControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void addPerson() {
		Person person1 = new Person().setFirstname("Aarthi").setLastname("N").setDob("17/09/1976")
				.setAddress("Chennai");
		String result = restTemplate.postForObject(getPersonURL(MappingURL.PERSON_ADD), new Person[] { person1 },
				String.class);
		Assert.assertEquals(ResponseMessage.ADDED, result);
		addPet();
	}

	@Test
	public void addPet() {

		Map<String, Object> map = new HashMap<>();
		map.put("name", "cat");
		map.put("age", 9);
		map.put("person_id", 1);

		String result = restTemplate.postForObject(getPetURL(MappingURL.PET_ADD), map, String.class);
		Assert.assertEquals(ResponseMessage.ADDED, result);
	}
	

	@Test
	public void testGetPetListService() throws Exception {
		List<Pet> petList = getAllPets();
		petList.forEach(p -> System.out.println(p));
		assertPet(petList.get(0), "cat", 9);
		
	}

	private String getPetURL(String suffix) {
		return "http://localhost:" + port + "/petdetails" + suffix;
	}

	private String getPersonURL(String suffix) {
		return "http://localhost:" + port + "/persondetails" + suffix;
	}
	
	private List<Pet> getAllPets() {
		ResponseEntity<List<Pet>> response = restTemplate.exchange(getPetURL(MappingURL.PET_GET_ALL), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Pet>>() {
				});
		return response.getBody();
	}
	
	private void assertPet(Pet pet, String name, int age) {
		Assert.assertEquals(name, pet.getName());
		Assert.assertEquals(age, pet.getAge());
		
	}

}
