package com.recommendation.job;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.recommendation.model.Product;
import com.recommendation.model.ProductPopularity;
import com.recommendation.model.enums.PopularityRating;
import com.recommendation.repository.ProductPopularityRepository;
import com.recommendation.repository.ProductRepository;

/**
 * @author Slobodan Erakovic
 */
@Component
public class ProductPopularityUpdatedJob {
	private static final Logger LOG = LoggerFactory.getLogger(ProductPopularityUpdatedJob.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductPopularityRepository ppRepository;

	/**
	 * This is the keypoint of recommendation strategy TopN products It will run
	 * at midnight every 1 of the month, and collect star ratings product
	 * obtained during the last month. Then we create appropriate
	 * ProductPopularity object, which is going to store information about each
	 * product, and his popularity for particular month and year. After such a
	 * popularity update, star rating of product is reset to 0, and process is
	 * repeated each month. P.S It could also go into more granular level (day,
	 * hours), but I think that point is clear enough
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	@Transactional
	public void executePopularityUpdate() {
		LOG.info("Start ProductPopularity update job...");
		StopWatch watch = new StopWatch();
		watch.start();

		List<Product> products = productRepository.query("SELECT p FROM Product p", Product.class).getResultList();
		LOG.info("Update ProductPopularity for {} products", products.size());
		DateTime now = new DateTime();

		for (Product p : products) {
			ProductPopularity pp = new ProductPopularity();
			int month = now.getMonthOfYear();
			int year = now.getYear();

			/**
			 * Since this is first second of new month, we are interesting in
			 * the previous month. In case of January, we are gathering for
			 * December
			 */
			month = month == 1 ? 12 : (month - 1);
			year = month == 1 ? year - 1 : year;

			pp.setMonth(month);
			pp.setYear(year);
			pp.setProduct(p);

			Integer starRating = p.getStarRating();
			pp.setPopularityRating(PopularityRating.getPopularity(starRating));

			ppRepository.persist(pp);
			p.setStarRating(0);
			productRepository.merge(p);
		}

		watch.stop();
		double totalTimeMinutes = watch.getTotalTimeSeconds() / 60;
		LOG.info("Finished ProductPopularity update job! It took: {}, seconds, or approximately: {} minutes ",
				watch.getTotalTimeSeconds(), String.format("%.2f", totalTimeMinutes));
	}
}
