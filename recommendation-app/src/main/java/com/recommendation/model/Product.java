package com.recommendation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.recommendation.util.Constants;

/**
 * @author Slobodan Erakovic
 */
@Entity
@Table(name = "product", schema = Constants.SCHEMA_NAME)
public class Product extends AbstractEntity {

	public enum ProductStatus {
		ACTIVE, INACTIVE
	}

	@Id
	@NotNull
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(generator = "ENTITY_ID_GENERATOR")
	@GenericGenerator(name = "ENTITY_ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "application.seq_product"),
			@Parameter(name = "optimizer", value = "hilo"), @Parameter(name = "increment_size", value = "1") })
	private long id;

	@NotNull
	@Column(name = "name", nullable = false, updatable = false)
	private String name;

	@NotNull
	@Column(name = "star_rating", nullable = false, updatable = false)
	private Integer starRating;

	@NotNull
	@Column(name = "description", nullable = false, updatable = false)
	private String description;

	// Is there a need for this to hold? Because of @Transaction would return
	// ALL product populatiry
	// @OneToMany(mappedBy = "product", cascade = { CascadeType.ALL }, fetch =
	// FetchType.LAZY)
	// private List<ProductPopularity> productPopularities =
	// Lists.newArrayList();

	@NotNull
	@Column(name = "status", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStarRating() {
		return starRating;
	}

	public void setStarRating(Integer starRating) {
		this.starRating = starRating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	// public List<ProductPopularity> getProductPopularities() {
	// return productPopularities;
	// }

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
		return "Product [id=" + id + ", name=" + name + ", starRating=" + starRating + ", description=" + description
				+ ", status=" + status + "]";
	}

}
