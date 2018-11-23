package com.recommendation.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recommendation.model.enums.Interaction;
import com.recommendation.model.enums.Strategy;
import com.recommendation.model.enums.UserGroup;

/**
 * @author Slobodan Erakovic
 */
public class RecommendationDTO {

	public static class TimeRange {

		/**
		 * 0 mean current type of date-time (current Year, current Month,
		 * current Day) , if field is absent for month (=null), then we search
		 * only for year. Year is mandatory field, sort = 1; means default
		 * sorting descending
		 */
		private Integer yearsAgo;
		private Integer monthsAgo;
		private int sort = 1;

		public Integer getYearsAgo() {
			return yearsAgo;
		}

		public void setYearsAgo(Integer yearsAgo) {
			this.yearsAgo = yearsAgo;
		}

		public Integer getMonthsAgo() {
			return monthsAgo;
		}

		public void setMonthsAgo(Integer monthsAgo) {
			this.monthsAgo = monthsAgo;
		}

		public int getSort() {
			return sort;
		}

		public void setSort(int sort) {
			this.sort = sort;
		}

		@Override
		public String toString() {
			return "TimeRange [yearsAgo=" + yearsAgo + ", monthsAgo=" + monthsAgo + ", sort=" + sort + "]";
		}

	}

	private int length;
	private Strategy strategy;
	private UserGroup userGroup;
	private TimeRange timeRange;
	private Interaction interaction;
	private Long customerId;

	@JsonCreator
	public RecommendationDTO(@JsonProperty("length") final int length,
			@JsonProperty("strategy") final Strategy strategy, @JsonProperty("userGroup") final UserGroup userGroup,
			@JsonProperty("timeRange") final TimeRange timeRange,
			@JsonProperty("interaction") final Interaction interaction,
			@JsonProperty("customerId") final Long customerId) {

		this.length = length;
		this.strategy = strategy;
		this.userGroup = userGroup;
		this.timeRange = timeRange;
		this.interaction = interaction;
		this.customerId = customerId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public TimeRange getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(TimeRange timeRange) {
		this.timeRange = timeRange;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "RecommendationDTO [length=" + length + ", strategy=" + strategy + ", userGroup=" + userGroup
				+ ", timeRange=" + timeRange + ", interaction=" + interaction + ", customerId=" + customerId + "]";
	}

}
