package com.rabo.demo;

import java.util.List;

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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.constants.ResponseMessage;
import com.rabo.demo.model.Person;
import com.rabo.demo.model.PersonAddress;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class PersonControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void addPersons() {
		// Test addPerson service
		String result = restTemplate.postForObject(getPersonURL(MappingURL.PERSON_ADD), createPersons(), String.class);
		Assert.assertEquals(ResponseMessage.ADDED, result);
	}

	@AfterEach
	public void deletePersons() {
		restTemplate.delete(getPersonURL(MappingURL.PERSON_DELETE_ALL));
	}

	void contextLoads() {
	}

	private Person[] createPersons() {
		Person person1 = new Person().setFirstname("Aarthi").setLastname("N").setDob("17/09/1976")
				.setAddress("Chennai");
		Person person2 = new Person().setFirstname("Jana").setLastname("N").setDob("01/01/1980").setAddress("Namakkal");
		Person person3 = new Person().setFirstname("Jaya").setLastname("Eswar").setDob("11/11/1990")
				.setAddress("Bangalore");
		return new Person[] { person1, person2, person3 };
	}

	private String getPetURL(String suffix) {
		return "http://localhost:" + port + "/petdetails" + suffix;
	}

	private String getPersonURL(String suffix) {
		return "http://localhost:" + port + "/persondetails" + suffix;
	}

	@Test
	public void testGetPersonListService() throws Exception {
		List<Person> personList = getAllPersons();
		personList.forEach(p -> System.out.println(p));
		assertPerson(personList.get(0), "Aarthi", "N", "Chennai", "17/09/1976");
		assertPerson(personList.get(1), "Jana", "N", "Namakkal", "01/01/1980");
		assertPerson(personList.get(2), "Jaya", "Eswar", "Bangalore", "11/11/1990");
	}

	@Test
	public void testGetByPersonIdService() {
		Person person = getPerson(3);
		assertPerson(person, "Jaya", "Eswar", "Bangalore", "11/11/1990");
	}

	@Test
	public void testUpdateAddressService() throws Exception {
		restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		PersonAddress address = new PersonAddress();
		address.setAddress("Pondicherry");
		address.setId(searchByName("Aarthi", null).get(0).getId());
		String result = restTemplate.patchForObject(getPersonURL(MappingURL.PERSON_UPDATE), address, String.class);
		Assert.assertEquals(ResponseMessage.UPDATED, result);

		Person person = searchByName("Aarthi", null).get(0);
		assertPerson(person, "Aarthi", "N", "Pondicherry", "17/09/1976");
	}

	@Test
	public void testDeletePersonService() throws Exception {
		Person person = searchByName("Aarthi", null).get(0);
		Assert.assertNotNull(person);
		restTemplate.delete(getPersonURL(MappingURL.PERSON_DELETE_BY_ID), person.getId());
		Assert.assertThrows(RestClientException.class, () -> searchByName("Aarthi", null));
	}

	@Test
	public void testDeleteAllPersonService() throws Exception {
		Assert.assertEquals(3, getAllPersons().size());
		restTemplate.delete(getPersonURL(MappingURL.PERSON_DELETE_ALL));
		List<Person> personList = getAllPersons();
		Assert.assertTrue(CollectionUtils.isEmpty(personList));
	}

	@Test
	public void testSearchByNameService() throws Exception {

		List<Person> personList = searchByName("Aarthi", "N");
		Assert.assertEquals(1, personList.size());

		personList = searchByName(null, "N");
		Assert.assertEquals(2, personList.size());
		Assert.assertThrows(RestClientException.class, () -> searchByName("ABC", "XYZ"));
	}

	private List<Person> searchByName(String firstName, String lastName) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromHttpUrl(getPersonURL(MappingURL.PERSON_SEARCH_BY_NAME)).queryParam("firstName", firstName)
				.queryParam("lastName", lastName);

		ResponseEntity<List<Person>> listPersonResponse = restTemplate.exchange(uriBuilder.toUriString(),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
				});

		return listPersonResponse.getBody();
	}

	private void assertPerson(Person person, String firstName, String lastName, String address, String dob) {
		Assert.assertEquals(firstName, person.getFirstname());
		Assert.assertEquals(lastName, person.getLastname());
		Assert.assertEquals(address, person.getAddress());
		Assert.assertEquals(dob, person.getDob());
	}

	private Person getPerson(int id) {
		ResponseEntity<Person> responsePerson = restTemplate.exchange(getPersonURL(MappingURL.PERSON_GET_BY_ID),
				HttpMethod.GET, null, new ParameterizedTypeReference<Person>() {
				}, Integer.valueOf(id));
		return responsePerson.getBody();
	}

	private List<Person> getAllPersons() {
		ResponseEntity<List<Person>> response = restTemplate.exchange(getPersonURL(MappingURL.PERSON_GET_ALL),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
				});
		return response.getBody();
	}

	/*
	 * @Test public void testGetPersonList() { URI uri = new
	 * URI("http://localhost:"+port+"/persons"); String msg =
	 * testTemplate.getForObject(uri, Person.class); }
	 */

}
