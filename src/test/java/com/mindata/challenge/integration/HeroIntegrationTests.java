package com.mindata.challenge.integration;

import com.mindata.challenge.model.dto.HeroRequestDTO;
import com.mindata.challenge.model.dto.HeroResponseDTO;
import com.mindata.challenge.model.entity.Hero;
import com.mindata.challenge.repository.HeroRepository;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HeroIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private HeroRepository heroRepository;

	private final String url = "api/v1/hero";

	@Test
	@DisplayName("Create Hero should return 200")
	public void createHero_should_createHero() {

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("name");

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(heroRequest), HeroResponseDTO.class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertNotNull(response.getBody().getId());
		Assertions.assertNotNull(response.getBody().getName());

	}

	@Test
	@DisplayName("Create Hero with null name should return 400")
	public void createHero_should_validateRequest_1() {

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName(null);

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(heroRequest), HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Create Hero with empty name should return 400")
	public void createHero_should_validateRequest_2() {

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("");

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(heroRequest), HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Create Hero using non-alphabetic characters should return 400")
	public void createHero_should_validateRequest_3() {

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("12$5!");

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(heroRequest), HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Create Hero with empty request body should return 400")
	public void createHero_should_validateRequest_4() {

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(null), HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Create Hero duplicated should return 409")
	public void createHero_should_validateRequest_5() {

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("Spiderman");

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.postForEntity(
				String.format("http://localhost:%d/%s", port, url), httpEntity(heroRequest), HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	@DisplayName("Get Hero by ID should return 200")
	public void getHero_should_getHero() {

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 5),
				HttpMethod.GET,
				httpEntity(null),
				HeroResponseDTO.class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertNotNull(response.getBody().getId());
		Assertions.assertNotNull(response.getBody().getName());
	}

	@Test
	@DisplayName("Get Hero with non-existent client id should return 404")
	public void getHero_should_validateRequest_1() {

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 150),
				HttpMethod.GET,
				httpEntity(null),
				HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@DisplayName("Get Hero with invalid client id should return 400")
	public void getHero_should_validateRequest_2() {

		ResponseEntity<HeroResponseDTO> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, null),
				HttpMethod.GET,
				httpEntity(null),
				HeroResponseDTO.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Get Heroes by name should return 200")
	public void getHeroesByName_should_getHeroes() {

		String name = "man";

		ResponseEntity<HeroResponseDTO[]> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s?name=%s", port, url, name),
				HttpMethod.GET,
				httpEntity(null),
				HeroResponseDTO[].class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		Assertions.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Get All Heroes should return 200")
	public void getAllHeroes_should_getAllHeroes() {

		ParameterizedTypeReference<RestResponsePage<HeroResponseDTO>> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<RestResponsePage<HeroResponseDTO>> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/all?page=%d&size=%d&sort=%s", port, url, 0, 10, "asc"),
				HttpMethod.GET,
				httpEntity(null),
				responseType);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(10, response.getBody().getSize());
	}

	@Test
	@DisplayName("Delete Hero should return 200")
	public void deleteHero_should_deleteHero(){

				ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 1),
				HttpMethod.DELETE,
				httpEntity(null),
				Void.class);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	@DisplayName("Delete Hero with non-existent client id should return 404")
	public void deleteHero_should_validateRequest_1(){

		ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 200L),
				HttpMethod.DELETE,
				httpEntity(null),
				Void.class);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@DisplayName("Delete Hero with invalid client id should return 400")
	public void deleteHero_should_validateRequest_2(){

		ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%s", port, url, null),
				HttpMethod.DELETE,
				httpEntity(null),
				Void.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Update Hero should return 200")
	public void updateHero_should_updateHero(){

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("modifyName");

		ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 5),
				HttpMethod.PUT,
				httpEntity(heroRequest),
				Void.class);

		Hero updatedHero = heroRepository.findById(5L).orElse(null);

		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		Assertions.assertNotNull(updatedHero);
		Assertions.assertEquals("modifyName", updatedHero.getName());
	}

	@Test
	@DisplayName("Update Hero with non-existent client id should return 404")
	public void updateHero_should_validateRequest_1(){

		HeroRequestDTO heroRequest = new HeroRequestDTO();
		heroRequest.setName("modifyName");

		ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 250),
				HttpMethod.PUT,
				httpEntity(heroRequest),
				Void.class);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@DisplayName("Update Hero with invalid request should return 400")
	public void updateHero_should_validateRequest_2(){

		ResponseEntity<Void> response = testRestTemplate.exchange(
				String.format("http://localhost:%d/%s/%d", port, url, 250),
				HttpMethod.PUT,
				httpEntity(null),
				Void.class);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private HttpEntity<HeroRequestDTO> httpEntity(HeroRequestDTO requestBody) {
		return new HttpEntity<>(requestBody, headers());
	}

	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		String auth = "admin:admin";
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
