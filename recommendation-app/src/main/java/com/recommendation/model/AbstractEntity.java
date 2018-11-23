package com.recommendation.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Slobodan Erakovic - Used as place holder for certain properties
 */
@MappedSuperclass
public abstract class AbstractEntity {

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, updatable = false)
	protected Date creationDate;

	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@PrePersist
	public void prePersist() {
		final Date currentDate = new Date();
		if (creationDate == null) {
			creationDate = currentDate;
		}
	}
}
