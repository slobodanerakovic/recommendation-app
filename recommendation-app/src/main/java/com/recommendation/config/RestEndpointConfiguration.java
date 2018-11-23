package com.recommendation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.recommendation.web.InteractionsEndpoint;
import com.recommendation.web.ProductsEndpoint;
import com.recommendation.web.RecomendationsEndpoint;

/**
 * @author Slobodan Erakovic
 */
@Component
public class RestEndpointConfiguration extends ResourceConfig {
	public RestEndpointConfiguration() {
		register(InteractionsEndpoint.class).register(ProductsEndpoint.class).register(RecomendationsEndpoint.class);
	}
}
