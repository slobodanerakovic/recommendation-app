package com.recommendation.model.enums;

/**
 * @author Slobodan Erakovic
 */
public enum UserGroup {
	CHILD(15), YOUNG(25), MIDDLE_AGE(45), YOUNG_SENIOR(60), SENIOR(100);

	private int upToAge;

	UserGroup(int upToAge) {
		this.upToAge = upToAge;
	}

	public int getUpToAge() {
		return this.upToAge;
	}

}
