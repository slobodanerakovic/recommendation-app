package com.recommendation.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.recommendation.model.enums.Status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductsEndpointTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testProductsRestoring() {
		long productId = 123456789;

		ResponseEntity<Status> entity = restTemplate.getForEntity(
				String.format("/api/products/restore/%d/", productId) + "?adminid=DDXDpn1fw6s%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.AUTHORISATION_FAILED, entity.getBody());

		entity = restTemplate.getForEntity("/api/products/restore/all/?adminid=DDXDpn1fw6s%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.AUTHORISATION_FAILED, entity.getBody());

		entity = restTemplate.getForEntity(
				String.format("/api/products/restore/%d/", productId) + "?adminid=DDfDpQ%3D%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.SUCCESS, entity.getBody());

		entity = restTemplate.getForEntity("/api/products/restore/all/?adminid=DDfDpQ%3D%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.SUCCESS, entity.getBody());

	}

	@Test
	public void testProductsBlocking() {
		long productId = 123456789;

		ResponseEntity<Status> entity = restTemplate.getForEntity(
				String.format("/api/products/block/%d/", productId) + "?adminid=DDXDpn1fw6s%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.AUTHORISATION_FAILED, entity.getBody());

		entity = restTemplate.getForEntity("/api/products/block/all/?adminid=DDXDpn1fw6s%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.AUTHORISATION_FAILED, entity.getBody());

		entity = restTemplate.getForEntity(
				String.format("/api/products/block/%d/", productId) + "?adminid=DDfDpQ%3D%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.SUCCESS, entity.getBody());

		entity = restTemplate.getForEntity("/api/products/block/all/?adminid=DDfDpQ%3D%3D", Status.class);
		Assert.assertEquals(entity.getStatusCode().toString(), "200");
		Assert.assertEquals(Status.SUCCESS, entity.getBody());
	}

}
