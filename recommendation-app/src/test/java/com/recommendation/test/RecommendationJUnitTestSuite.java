package com.recommendation.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Slobodan Erakovic
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ProductsEndpointTest.class, ProductPopularityJobTest.class, 
		InteractionsEndpointTest.class, RecommendationsEndpointTest.class })
public class RecommendationJUnitTestSuite {

}
