package com.recommendation.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.recommendation.model.enums.PopularityRating;
import com.recommendation.util.Constants;

/**
 * @author Slobodan Erakovic
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "product_popularity", schema = Constants.SCHEMA_NAME)
public class ProductPopularity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4381503267001985706L;

	@Id
	@NotNull
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(generator = "ENTITY_ID_GENERATOR")
	@GenericGenerator(name = "ENTITY_ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "application.seq_product_popularity"),
			@Parameter(name = "optimizer", value = "hilo"), @Parameter(name = "increment_size", value = "1") })
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false, updatable = false)
	private Product product;

	@NotNull
	@Column(name = "popularity_rating", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private PopularityRating popularityRating;

	@NotNull
	@Column(name = "year", nullable = false, updatable = false)
	private Integer year;

	@NotNull
	@Column(name = "month", nullable = false, updatable = false)
	private Integer month;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PopularityRating getPopularityRating() {
		return popularityRating;
	}

	public void setPopularityRating(PopularityRating popularityRating) {
		this.popularityRating = popularityRating;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return "ProductPopularity [id=" + id + ", product=" + product + ", popularityRating=" + popularityRating
				+ ", year=" + year + ", month=" + month + "]";
	}

}
