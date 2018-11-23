package com.recommendation.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.recommendation.job.ProductPopularityUpdatedJob;
import com.recommendation.model.Product;
import com.recommendation.repository.ProductRepository;

/**
 * @author Slobodan Erakovic
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductPopularityJobTest {

	@Autowired
	private ProductPopularityUpdatedJob job;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@Transactional
	public void testPopularityJob() {
		job.executePopularityUpdate();
		List<Product> products = productRepository.query("SELECT p FROM Product p", Product.class).getResultList();

		for (Product p : products) {
			Assert.assertEquals("0", p.getStarRating().toString());
		}
	}

}
