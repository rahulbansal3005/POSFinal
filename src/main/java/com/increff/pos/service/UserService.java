package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.UserDao;
import com.increff.pos.pojo.UserPojo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(UserPojo userPojo) throws ApiException {
		UserPojo existing = userDao.select(userPojo.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		userDao.insert(userPojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public UserPojo get(String email) throws ApiException {
		return userDao.select(email);
	}

	@Transactional(rollbackOn = ApiException.class)
	public List<UserPojo> getAll() {
		return userDao.selectAll();
	}

//	@Transactional(rollbackOn = ApiException.class)
//	public void delete(int id) {
//		userDao.delete(id);
//	}


}
