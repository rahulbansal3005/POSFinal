package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.UserDao;
import com.increff.employee.pojo.UserPojo;

import static com.increff.employee.util.Normalize.normalize;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
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

	@Transactional
	public List<UserPojo> getAll() {
		return userDao.selectAll();
	}

	@Transactional
	public void delete(int id) {
		userDao.delete(id);
	}


}
