package com.rabo.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

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


	@Test
	public void addPet() {
		Pet pet = new Pet();
		pet.setName("cat");
		pet.setAge(9);

		String result = restTemplate.postForObject(getPetURL(MappingURL.PET_ADD), new Pet[] { pet }, String.class);
		Assert.assertEquals(ResponseMessage.ADDED, result);

	}

	@Test
	public void testGetPetListService() throws Exception {
		List<Pet> petList = getAllPets();
		Assert.assertEquals(1, petList.size());
		petList.forEach(p -> System.out.println(p));
		Pet pet = petList.get(0);
		assertPet(pet, "cat", 9);
		/* restTemplate.delete(getPetURL(MappingURL.PET_DELETE_ALL)); */

		restTemplate.delete(getPetURL(MappingURL.PET_DELETE_BY_ID), pet.getPet_id());
		petList = getAllPets();
		Assert.assertTrue(CollectionUtils.isEmpty(petList));
		petList.forEach(p -> System.out.println(p));

	}

	private String getPetURL(String suffix) {
		return "http://localhost:" + port + "/petdetails" + suffix;
	}

	private String getPersonURL(String suffix) {
		return "http://localhost:" + port + "/persondetails" + suffix;
	}

	private List<Pet> getAllPets() {
		ResponseEntity<List<Pet>> response = restTemplate.exchange(getPetURL(MappingURL.PET_GET_ALL), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Pet>>() {
				});
		return response.getBody();
	}

	private void assertPet(Pet pet, String name, int age) {
		Assert.assertEquals(name, pet.getName());
		Assert.assertEquals(age, pet.getAge());

	}

}
