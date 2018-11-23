package com.recommendation.test;

import java.io.IOException;

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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommendation.model.Product;
import com.recommendation.model.enums.Interaction;
import com.recommendation.model.enums.Strategy;
import com.recommendation.model.enums.UserGroup;
import com.recommendation.web.dto.RecommendationDTO;
import com.recommendation.web.dto.RecommendationDTO.TimeRange;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecommendationsEndpointTest {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationsEndpointTest.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testRandomRecommendation() throws JSONException {
		TimeRange tr = new TimeRange();
		tr.setMonthsAgo(4);
		tr.setYearsAgo(2015);

		// TODO: Does UserGroup.MIDDLE_AGE makes any sense to be searchable
		// condition ???
		RecommendationDTO dto = new RecommendationDTO(3, Strategy.RANDOM, UserGroup.MIDDLE_AGE, tr, Interaction.VIEWED,
				2131231311L);
		ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/recomendations/search", dto,
				String.class);
		testAssertations(entity, 3, null);
	}

	@Test
	public void testTopNRecommendation() throws JSONException {
		TimeRange tr = new TimeRange();
		tr.setMonthsAgo(3);
		tr.setYearsAgo(2015);

		RecommendationDTO dto = new RecommendationDTO(2, Strategy.TOP_N, UserGroup.MIDDLE_AGE, tr, Interaction.VIEWED,
				1L);
		ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/recomendations/search", dto,
				String.class);
		testAssertations(entity, 2, null);

		// none exists for this month of 2015
		tr.setMonthsAgo(7);
		dto = new RecommendationDTO(2, Strategy.TOP_N, UserGroup.MIDDLE_AGE, tr, Interaction.VIEWED, 1L);
		entity = this.restTemplate.postForEntity("/api/recomendations/search", dto, String.class);
		testAssertations(entity, 0, null);

		tr.setMonthsAgo(7);
		tr.setYearsAgo(2017);
		dto = new RecommendationDTO(0, Strategy.TOP_N, UserGroup.MIDDLE_AGE, tr, Interaction.VIEWED, 1L);
		entity = this.restTemplate.postForEntity("/api/recomendations/search", dto, String.class);
		testAssertations(entity, 1, null);
	}

	@Test
	public void testBasicCollaborationRecommendation() throws JSONException {
		TimeRange tr = new TimeRange();
		tr.setMonthsAgo(3);
		tr.setYearsAgo(2015);

		RecommendationDTO dto = new RecommendationDTO(2, Strategy.BASIC_COLLABORATION, UserGroup.MIDDLE_AGE, tr,
				Interaction.VIEWED, 999L);
		ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/recomendations/search", dto,
				String.class);
		testAssertations(entity, 1, null);
	}

	@Test
	public void testUserPreferenceRecommendation() throws JSONException {
		TimeRange tr = new TimeRange();
		tr.setMonthsAgo(3);
		tr.setYearsAgo(2015);

		RecommendationDTO dto = new RecommendationDTO(2, Strategy.USER_PREFERENCE, UserGroup.CHILD, tr,
				Interaction.VIEWED, 444L);
		ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/recomendations/search", dto,
				String.class);
		testAssertations(entity, 1, "FLOWER");
	}

	@Ignore
	@Test
	public void testMixedUserCollaborationRecommendation() throws JSONException {
		TimeRange tr = new TimeRange();
		tr.setMonthsAgo(3);
		tr.setYearsAgo(2015);

		RecommendationDTO dto = new RecommendationDTO(2, Strategy.MIXTURE_USER_COLLABORATIONS, UserGroup.MIDDLE_AGE, tr,
				Interaction.VIEWED, 999L);
		ResponseEntity<String> entity = this.restTemplate.postForEntity("/api/recomendations/search", dto,
				String.class);
		testAssertations(entity, 1, null);
	}

	private void testAssertations(ResponseEntity<String> entity, int expected, String productName)
			throws JSONException {
		Assert.assertEquals(entity.getStatusCode().toString(), "200");

		Product expectedProduct = null;
		JSONArray jsonArray = new JSONArray(entity.getBody());
		LOG.info("Mocked returned Json={}", jsonArray);
		try {
			Assert.assertEquals(expected, jsonArray.length());
			if (expected == 0)
				return;
			expectedProduct = mapper.readValue(jsonArray.get(0).toString(), Product.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(expectedProduct instanceof Product);
		if (productName != null)
			Assert.assertEquals(expectedProduct.getName(), productName);
	}
}
