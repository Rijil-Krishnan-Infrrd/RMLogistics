package com.nexera.core.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserProfileDao userProfileDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Override
	public void validateUser(User user, String password)
			throws InvalidInputException {

		//TODO: Implement the logic

	}

	@Override
	public User getUserWithLoginName(String userName)
			throws NoRecordsFetchedException,DatabaseException {

		LOG.info("Fetching the user object from User Dao");
		return userProfileDao.findByUserName(userName);

	}

}
