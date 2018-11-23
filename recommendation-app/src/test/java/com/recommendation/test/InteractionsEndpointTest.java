package com.recommendation.test;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommendation.model.Product;
import com.recommendation.model.enums.Interaction;
import com.recommendation.util.StandardLib;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InteractionsEndpointTest {

	private static final Logger LOG = LoggerFactory.getLogger(InteractionsEndpointTest.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testProductsByCustomerInteraction_NoMock() throws JSONException {
		String url = String.format("/api/interaction/%s/%s", Interaction.VIEWED, StandardLib.encodeId(999));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, "BALL", 1);

		url = String.format("/api/interaction/%s/%s", Interaction.COMMENTED, StandardLib.encodeId(444));
		entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, "HOUSE", 1);

		url = String.format("/api/interaction/%s/%s", Interaction.BOUGHT, StandardLib.encodeId(777));
		entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, "FLOWER", 1);
	}

	@Test
	public void mixedInteractionByCustomer_NoMock() throws JSONException {
		String url = String.format("/api/interaction/%s/%s/?length=2", Interaction.VIEWED_BOUGHT,
				StandardLib.encodeId(888));
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, "CAT", 1);

		url = String.format("/api/interaction/%s/%s", Interaction.BOUGHT_COMMENTED, StandardLib.encodeId(666));
		entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, "CAR", 1);
	}

	private void testAssertations(ResponseEntity<String> entity, String productName, int expected)
			throws JSONException {
		Assert.assertEquals(entity.getStatusCode().toString(), "200");

		Product expectedProduct = null;
		JSONArray jsonArray = new JSONArray(entity.getBody());
		LOG.info("Mocked returned Json={}", jsonArray);
		try {
			Assert.assertEquals(expected, jsonArray.length());
			expectedProduct = mapper.readValue(jsonArray.get(0).toString(), Product.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(expectedProduct instanceof Product);
		Assert.assertEquals(expectedProduct.getName(), productName);
	}

}