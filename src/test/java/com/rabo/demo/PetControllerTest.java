package com.rabo.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.demo.constants.MappingURL;
import com.rabo.demo.constants.ResponseMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class PetControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	@Test
	public void addPet() {
		Map<String,Object> map = new HashMap<>();
		map.put("name", "cat");
		map.put("age", 9);
		map.put("person_id", 1);
		
		String result = restTemplate.postForObject(getURL("/pet"), map, String.class);
		Assert.assertEquals(ResponseMessage.ADDED, result);
	}
	
	private String getURL(String suffix) {
		return "http://localhost:" + port + "/petdetails" + suffix;
	}
}
