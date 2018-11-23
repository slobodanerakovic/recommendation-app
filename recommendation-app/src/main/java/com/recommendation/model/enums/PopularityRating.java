package com.recommendation.model.enums;

/**
 * @author Slobodan Erakovic - Product popularity depends on the number of stars
 *         product get obtained during his existence (overal populatiry) or
 *         during specific time period. (eg. in 2010 it got 230 stars - it was
 *         good rated (upt to 500) The more it has, popularity is increased
 */
public enum PopularityRating {
	A_UNRATED(0), B_LOW_RATING(100), C_GOOD_RATING(500), D_VERY_GOOD_RATING(1000), E_TOP_RATING(1001);

	private int upToRatingStars;

	PopularityRating(int upToRatingStars) {
		this.upToRatingStars = upToRatingStars;
	}

	public int getRatingStars() {
		return this.upToRatingStars;
	}

	public static PopularityRating getPopularity(int ratingStars) {

		if (ratingStars == 0) {
			return PopularityRating.A_UNRATED;

		} else if (ratingStars <= B_LOW_RATING.getRatingStars() && ratingStars > A_UNRATED.getRatingStars()) {
			return PopularityRating.B_LOW_RATING;

		} else if (ratingStars <= C_GOOD_RATING.getRatingStars() && ratingStars > B_LOW_RATING.getRatingStars()) {
			return PopularityRating.C_GOOD_RATING;

		} else if (ratingStars <= D_VERY_GOOD_RATING.getRatingStars() && ratingStars > C_GOOD_RATING.getRatingStars()) {
			return PopularityRating.D_VERY_GOOD_RATING;

		} else {
			return PopularityRating.E_TOP_RATING;
		}
	}
}
