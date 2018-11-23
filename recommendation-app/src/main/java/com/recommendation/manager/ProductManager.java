package com.recommendation.manager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.glassfish.jersey.Beta;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.recommendation.model.Product;
import com.recommendation.model.User;
import com.recommendation.model.UserPreference;
import com.recommendation.model.Product.ProductStatus;
import com.recommendation.model.enums.Interaction;
import com.recommendation.model.enums.Status;
import com.recommendation.repository.ProductRepository;
import com.recommendation.util.NoImplementedOperation;
import com.recommendation.web.dto.RecommendationDTO;
import com.recommendation.web.dto.RecommendationDTO.TimeRange;

/**
 * @author Slobodan Erakovic
 */
@Service
public class ProductManager {

	private static final Logger LOG = LoggerFactory.getLogger(ProductManager.class);
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final long startingProductId = 101;

	@Autowired
	private ProductRepository productRepository;

	public Status changeProductAvailibility(Long productid, ProductStatus status) {
		boolean update = productRepository.changeAvailability(productid, ProductStatus.ACTIVE);
		if (update) {
			LOG.info("Product status successfuly to be set to={}", status);
			return Status.SUCCESS;
		} else {
			LOG.info("Product status failed to be set to={}", status);
			return Status.SUCCESS;
		}
	}

	public List<Product> provideProductsByInteraction(long customerId, Interaction interaction, int length) {
		List<Product> products = Lists.newArrayList();
		LOG.info("Searching products with interaction={}, for customerId={}", interaction, customerId);

		products = productRepository.getUserInteractedProducts(customerId, interaction, length);
		return products;
	}

	public List<Product> getRandomProducts(int limit) {
		// Get number of products
		String sqlCount = "select count(*) from application.product";
		int randomFeed = countAsInt(productRepository.nativeQuery(sqlCount).getSingleResult());
		Set<Long> recProductIds = Sets.newHashSet();

		// Get unique product id, randomly chosen
		while (recProductIds.size() < limit) {
			recProductIds.add(RANDOM.nextInt(randomFeed) + startingProductId);
		}
		// Retrieve randomly chosen products
		List<Product> products = productRepository
				.collectRecommendationRandomProducts(new ArrayList<Long>(recProductIds));

		return products;
	}

	protected int countAsInt(Object result) {
		return ((Number) result).intValue();
	}

	/**
	 * TopN products can be recommended for specific time range, exclusively .
	 * Based on the TimeRange data, we can search for product which has been
	 * most popular for specific month of specific year, or products which were
	 * most popular on certain year. Default sorting is desc, which means, it
	 * will return a List of products, starting from most popular
	 * 
	 * @param length
	 * @param timeRange
	 * @return
	 */
	public List<Product> getTopNProducts(int length, TimeRange timeRange) {
		/**
		 * Check are requested top products for month, or specific month of the
		 * year of specific year
		 */
		int year = 99, month = 99;
		DateTime now = new DateTime();
		Preconditions.checkNotNull(timeRange.getYearsAgo(), "Year field must NOT be empty");

		if (timeRange.getYearsAgo() == 0) {
			year = now.getYear();
		} else {
			year = timeRange.getYearsAgo();
		}
		if (timeRange.getMonthsAgo() == 0) {
			month = now.getMonthOfYear();
		} else {
			month = timeRange.getMonthsAgo();
		}
		List<Product> products = productRepository.collectProductsOfTimeRange(length, year, month, timeRange.getSort());
		LOG.info("Products to return base on ={} timerange are ={}", timeRange, products.size());

		return products;
	}

	public List<Product> getProductsByUserPreference(RecommendationDTO dto, UserPreference userPreference) {
		List<Product> products = productRepository.collectPreferenceBasedProducts(dto.getCustomerId(), userPreference,
				dto.getLength());

		return products;
	}

	/**
	 * Basic Collaboration would represent a basic information about customer :
	 * age, country and gender get products of user with same age, same gender,
	 * same country
	 * 
	 * @param dto
	 * @param user
	 * @return
	 */
	public List<Product> getProductsByBasicCollaborations(RecommendationDTO dto, User user) {
		List<Product> products = productRepository.collectBasicCollaborationProducts(dto.getCustomerId(), user.getAge(),
				user.getGender(), user.getCountryIso(), dto.getInteraction(), dto.getLength());

		return products;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Beta
	@NoImplementedOperation
	public List<Product> getProductsByComplexMixtureOfCollaborations(RecommendationDTO dto) throws Exception {
		throw new Exception(
				"Currently this method is not is use, and require implementation, regarding business logic requirements");
	}
}
