package com.recommendation.manager;

import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.recommendation.model.Product;
import com.recommendation.model.User;
import com.recommendation.model.enums.Strategy;
import com.recommendation.repository.UserRepository;
import com.recommendation.web.dto.RecommendationDTO;

/**
 * @author Slobodan Erakovic
 */
@Service
public class RecommendationManager {

	private static final Logger LOG = LoggerFactory.getLogger(RecommendationManager.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductManager productManager;

	public List<Product> generateProductsRecommendation(final RecommendationDTO dto) {
		List<Product> products = Lists.newArrayList();

		Strategy strategy = dto.getStrategy();
		Preconditions.checkNotNull(strategy, "strategy cannot be null for Collaborative search");

		LOG.info("Strategy to apply={}", strategy);

		switch (strategy) {
		case RANDOM:
			products = productManager.getRandomProducts(dto.getLength());
			break;

		case TOP_N:
			products = productManager.getTopNProducts(dto.getLength(), dto.getTimeRange());
			break;

		// ***** Collaboration strategies starts here *****
		case BASIC_COLLABORATION:
			Preconditions.checkNotNull(dto.getCustomerId(), "customerId cannot be null for BASIC_COLLABORATION search");

			User userBasicC = userRepository.get(User.class, dto.getCustomerId());
			Preconditions.checkNotNull(userBasicC, "User cannot be null for Collaborative search");

			products = productManager.getProductsByBasicCollaborations(dto, userBasicC);
			break;

		case USER_PREFERENCE:
			Preconditions.checkNotNull(dto.getCustomerId(), "customerId cannot be null for USER_PREFERENCE search");

			User userForPref = userRepository.get(User.class, dto.getCustomerId());
			Preconditions.checkNotNull(userForPref, "User cannot be null for Collaborative search");

			products = productManager.getProductsByUserPreference(dto, userForPref.getUserPreference());
			break;

		case MIXTURE_USER_COLLABORATIONS:
			try {
				products = productManager.getProductsByComplexMixtureOfCollaborations(dto);
			} catch (Exception e) {
				Throwables.propagate(e);
			}
			break;

		}
		return products;
	}

}
