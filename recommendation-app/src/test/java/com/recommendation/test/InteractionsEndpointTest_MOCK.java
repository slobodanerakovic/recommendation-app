package com.recommendation.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.List;

import org.assertj.core.util.Lists;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommendation.manager.ProductManager;
import com.recommendation.model.Product;
import com.recommendation.model.enums.Interaction;
import com.recommendation.util.StandardLib;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore
public class InteractionsEndpointTest_MOCK {

	private static final Logger LOG = LoggerFactory.getLogger(InteractionsEndpointTest_MOCK.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private ProductManager productManager;

	@Test
	public void testProductsByCustomerInteraction() throws JSONException {
		long cusomerViewdId = 1234567891;
		long cusomerBoughtId = 1234567892;
		long cusomerCommentedId = 1234567893;

		// Mock productManager.provideProductsByInteraction so with difference
		// interactions, return different result
		Interaction viewed = Interaction.VIEWED;
		Interaction bought = Interaction.BOUGHT;
		Interaction commented = Interaction.COMMENTED;
		int length = 5;

		given(productManager.provideProductsByInteraction(cusomerViewdId, viewed, length))
				.willReturn(getMockProducts(viewed, null));
		given(productManager.provideProductsByInteraction(cusomerCommentedId, commented, length))
				.willReturn(getMockProducts(commented, null));
		given(productManager.provideProductsByInteraction(cusomerBoughtId, bought, length))
				.willReturn(getMockProducts(bought, null));

		// encodeId is an id which would be exchange over HTTP
		String encodeId = StandardLib.encodeId(cusomerViewdId);
		String url = String.format("/api/interaction/%s/%s", viewed, encodeId);
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, viewed, null);

		encodeId = StandardLib.encodeId(cusomerBoughtId);
		url = String.format("/api/interaction/%s/%s", bought, encodeId);
		entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, bought, null);

		encodeId = StandardLib.encodeId(cusomerCommentedId);
		url = String.format("/api/interaction/%s/%s", commented, encodeId);
		entity = this.restTemplate.getForEntity(url, String.class);
		testAssertations(entity, commented, null);
	}

	private void testAssertations(ResponseEntity<String> entity, Interaction inter, String data) throws JSONException {
		Assert.assertEquals(entity.getStatusCode().toString(), "200");

		Product expectedProduct = null;
		JSONArray jsonArray = new JSONArray(entity.getBody());
		LOG.info("Mocked returned Json={}", jsonArray);
		try {
			Assert.assertEquals(1, jsonArray.length());
			expectedProduct = mapper.readValue(jsonArray.get(0).toString(), Product.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(expectedProduct instanceof Product);
		Assert.assertEquals(expectedProduct.getName(), data != null ? data : inter.toString());
	}

	private List<Product> getMockProducts(Interaction inter, String data) {
		Product product = new Product();
		product.setId(123123);
		product.setName(data != null ? data : inter.toString());
		List<Product> productList = Lists.newArrayList(product);

		return productList;
	}
}