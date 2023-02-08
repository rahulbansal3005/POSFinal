package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.Data.UserData;
import com.increff.employee.model.Form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class AdminApiController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Adds a user")
	@RequestMapping(path = "/api/admin/user", method = RequestMethod.POST)
	public void addUser(@RequestBody UserForm userForm) throws ApiException {
		UserPojo userPojo = Helper.convertUserFormToUserPojo(userForm);
		service.add(userPojo);
	}

	@ApiOperation(value = "Deletes a user")
	@RequestMapping(path = "/api/admin/user/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) {
		service.delete(id);
	}

	@ApiOperation(value = "Gets list of all users")
	@RequestMapping(path = "/api/admin/user", method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		List<UserPojo> list = service.getAll();
		List<UserData> list2 = new ArrayList<UserData>();
		for (UserPojo userPojo : list) {
			list2.add(Helper.convertUserPojoToUserData(userPojo));
		}
		return list2;
	}

}
