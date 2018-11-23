package com.recommendation.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recommendation.model.User;
import com.recommendation.model.enums.Authorisation;
import com.recommendation.repository.UserRepository;

/**
 * @author Slobodan Erakovic
 */
@Service
public class UserManager {

	@Autowired
	private UserRepository userRepository;

	public boolean authenticateUserAction(long decryptedId) {
		User user = userRepository.get(User.class, decryptedId);

		if (user != null && user.getAuthorisation() == Authorisation.ADMIN) {
			return true;
		} else {
			return false;
		}
	}

}
