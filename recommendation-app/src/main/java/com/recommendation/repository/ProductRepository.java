package com.recommendation.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.recommendation.db.QuerySelector;
import com.recommendation.db.UpdateQuerySelector;
import com.recommendation.model.Product;
import com.recommendation.model.UserPreference;
import com.recommendation.model.Product.ProductStatus;
import com.recommendation.model.enums.Gender;
import com.recommendation.model.enums.Interaction;

import jersey.repackaged.com.google.common.collect.Lists;

/**
 * @author Slobodan Erakovic
 */
@Repository
public class ProductRepository extends DatabaseRepository<Product> {

	@Transactional
	public void resetProductRating(Long id) {
		String update = new String("UPDATE Product SET starRating = 0 WHERE id = :id");
		createUpdateQuery(update).setParameter("id", id).executeUpdate();
	}

	@Transactional
	public boolean changeAvailability(Long productId, ProductStatus status) {
		StringBuilder updateSQL = new StringBuilder("UPDATE Product SET status = :status");
		if (productId != null)
			updateSQL.append(" WHERE id = :productId");

		final UpdateQuerySelector update = createUpdateQuery(updateSQL.toString()).setParameter("status", status);

		if (productId != null)
			update.setParameter("productId", productId);

		int signal = update.executeUpdate();
		if (signal > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Product> getUserInteractedProducts(Long customerId, Interaction interaction, int limit) {
		StringBuilder selectSQL = new StringBuilder("SELECT p FROM UserTrackingBehaviour utb ")
				.append("INNER JOIN utb.product p ")
				.append("WHERE utb.user.id = :customerId AND utb.interaction = :interaction ORDER BY utb.creationDate DESC");
		List<Product> products = Lists.newArrayList();

		QuerySelector<Product> collectQuery = query(selectSQL.toString(), Product.class)
				.setParameter("customerId", customerId).setParameter("interaction", interaction);

		if (limit != 0)
			collectQuery.maxResults(limit);
		products = collectQuery.getResultList();

		return products;
	}

	public List<Product> collectRecommendationRandomProducts(ArrayList<Long> productIds) {
		List<Product> products = Lists.newArrayList();
		String sqlCollect = "SELECT p FROM Product p WHERE p.id IN (:productIds)";

		QuerySelector<Product> query = query(sqlCollect, Product.class).setParameter("productIds", productIds);
		products = query.getResultList();

		return products;
	}

	public List<Product> collectProductsOfTimeRange(int limit, Integer year, Integer month, int ordering) {
		StringBuilder sql = new StringBuilder(
				"SELECT p FROM ProductPopularity pp INNER JOIN pp.product p WHERE pp.year = :year");
		if (month != null)
			sql.append(" AND pp.month = :month");

		sql.append(" ORDER BY pp.popularityRating ");
		sql.append(ordering == 1 ? "DESC" : "ASC");

		QuerySelector<Product> collectQuery = query(sql.toString(), Product.class).setParameter("year", year);
		// If limit is == 0, means, return ALL without limit
		if (limit != 0)
			collectQuery.maxResults(limit);

		if (month != null)
			collectQuery.setParameter("month", month);

		List<Product> products = collectQuery.getResultList();

		return products;
	}

	public List<Product> collectPreferenceBasedProducts(Long customerId, UserPreference userPreference, int limit) {
		StringBuilder selectSQL = new StringBuilder("SELECT p FROM UserTrackingBehaviour utb ")
				.append("INNER JOIN utb.product p WHERE utb.user.id != :customerId ");

		if (userPreference.getActivityTime() != null)
			selectSQL.append("AND utb.user.userPreference.activityTime = :activityTime ");
		if (userPreference.getCreditCardType() != null)
			selectSQL.append("AND utb.user.userPreference.creditCardType = :creditCardType ");
		if (userPreference.getUserDevice() != null)
			selectSQL.append("AND utb.user.userPreference.userDevice = :userDevice ");
		if (userPreference.getUserGroup() != null)
			selectSQL.append("AND utb.user.userPreference.userGroup = :userGroup ");

		selectSQL.append("ORDER BY utb.creationDate DESC");

		QuerySelector<Product> collectQuery = query(selectSQL.toString(), Product.class);
		collectQuery.setParameter("customerId", customerId);

		if (userPreference.getActivityTime() != null)
			collectQuery.setParameter("activityTime", userPreference.getActivityTime());

		if (userPreference.getActivityTime() != null)
			collectQuery.setParameter("creditCardType", userPreference.getCreditCardType());

		if (userPreference.getActivityTime() != null)
			collectQuery.setParameter("userDevice", userPreference.getUserDevice());

		if (userPreference.getActivityTime() != null)
			collectQuery.setParameter("userGroup", userPreference.getUserGroup());

		if (limit != 0)
			collectQuery.maxResults(limit);

		List<Product> products = collectQuery.getResultList();

		return products;
	}

	public List<Product> collectBasicCollaborationProducts(Long customerId, Integer age, Gender gender,
			String countryIso, Interaction interaction, int limit) {
		StringBuilder selectSQL = new StringBuilder("SELECT p FROM UserTrackingBehaviour utb ")
				.append("INNER JOIN utb.product p WHERE utb.user.id != :customerId ");

		if (age != null)
			selectSQL.append("AND utb.user.age = :age ");
		if (gender != null)
			selectSQL.append("AND utb.user.countryIso = :countryIso ");
		if (countryIso != null)
			selectSQL.append("AND utb.user.gender = :gender ");
		if (interaction != null)
			selectSQL.append("AND utb.interaction = :interaction ");

		selectSQL.append("ORDER BY utb.creationDate DESC");

		QuerySelector<Product> collectQuery = query(selectSQL.toString(), Product.class);
		collectQuery.setParameter("customerId", customerId);

		if (age != null)
			collectQuery.setParameter("age", age);
		if (age != null)
			collectQuery.setParameter("countryIso", countryIso);
		if (age != null)
			collectQuery.setParameter("gender", gender);
		if (age != null)
			collectQuery.setParameter("interaction", interaction);

		if (limit != 0)
			collectQuery.maxResults(limit);

		List<Product> products = collectQuery.getResultList();

		return products;
	}
}
