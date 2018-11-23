package com.recommendation.web;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.recommendation.manager.RecommendationManager;
import com.recommendation.model.Product;
import com.recommendation.web.dto.RecommendationDTO;

/**
 * @author Slobodan Erakovic
 */
@Controller
@Path("/api/recomendations")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public class RecomendationsEndpoint {

	private static final Logger LOG = LoggerFactory.getLogger(RecomendationsEndpoint.class);

	@Autowired
	private RecommendationManager recommendationManager;

	/**
	 * Because there are lot of potentially given parameters for search, I decide to
	 * go with post, since I do not want that they are visible in browser, nor to do
	 * encrytion for each of them, etc. This is the entry point of recommendation ws
	 * 
	 * @param RecommendationDTO
	 *            dto
	 * @return List<Product>
	 */
	@POST
	@Path("/search")
	public Response searchProductsForRecommandations(final RecommendationDTO dto) {
		LOG.info(dto.toString());

		List<Product> recommendedProducts = recommendationManager.generateProductsRecommendation(dto);

		return Response.ok().entity(recommendedProducts).build();
	}

}
