package com.recommendation.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.recommendation.model.enums.ActivityTime;
import com.recommendation.model.enums.CreditCardType;
import com.recommendation.model.enums.UserDevice;
import com.recommendation.model.enums.UserGroup;

/**
 * @author Slobodan Erakovic
 */
@Embeddable
public class UserPreference implements Serializable {

	private static final long serialVersionUID = 6467602662655281393L;

	@Column(name = "activity_time")
	@Enumerated(EnumType.STRING)
	private ActivityTime activityTime;

	@Column(name = "credit_card_type")
	@Enumerated(EnumType.STRING)
	private CreditCardType creditCardType;

	@Column(name = "user_device")
	@Enumerated(EnumType.STRING)
	private UserDevice userDevice;

	@Column(name = "user_group")
	@Enumerated(EnumType.STRING)
	private UserGroup userGroup;

	public ActivityTime getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(ActivityTime activityTime) {
		this.activityTime = activityTime;
	}

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}

	public UserDevice getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(UserDevice userDevice) {
		this.userDevice = userDevice;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public String toString() {
		return "UserPreference [activityTime=" + activityTime + ", creditCardType=" + creditCardType + ", userDevice="
				+ userDevice + ", userGroup=" + userGroup + "]";
	}

}
