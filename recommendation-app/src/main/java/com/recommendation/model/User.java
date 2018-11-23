package com.recommendation.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.recommendation.model.enums.Authorisation;
import com.recommendation.model.enums.Gender;
import com.recommendation.util.Constants;

/**
 * @author Slobodan Erakovic
 */
@Access(AccessType.FIELD)
@Entity
@Table(name = "user", schema = Constants.SCHEMA_NAME)
public class User extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 3461867715751287765L;

	@Id
	@NotNull
	@Column(name = "id", nullable = false, updatable = false)
	@GeneratedValue(generator = "ENTITY_ID_GENERATOR")
	@GenericGenerator(name = "ENTITY_ID_GENERATOR", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "application.seq_user"),
			@Parameter(name = "optimizer", value = "hilo"), @Parameter(name = "increment_size", value = "1") })
	private long id;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<UserTrackingBehaviour> userTracker;

	@Embedded
	private UserPreference userPreference;

	@NotNull
	@Column(name = "country_iso", nullable = false)
	private String countryIso;

	@NotNull
	@Column(name = "authorisation")
	@Enumerated(EnumType.STRING)
	private Authorisation authorisation;

	@NotNull
	@Column(name = "gender", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@Column(name = "age", nullable = false)
	private Integer age;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserPreference getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}

	public String getCountryIso() {
		return countryIso;
	}

	public void setCountryIso(String countryIso) {
		this.countryIso = countryIso;
	}

	public List<UserTrackingBehaviour> getUserTracker() {
		return userTracker;
	}

	public Authorisation getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(Authorisation authorisation) {
		this.authorisation = authorisation;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setUserTracker(List<UserTrackingBehaviour> userTracker) {
		this.userTracker = userTracker;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
