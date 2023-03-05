package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.Data.UserData;
import com.increff.pos.model.Form.UserForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api
@RestController
@RequestMapping(value = "/api")
public class AdminApiController {

	@Autowired
	private AdminDto adminDto;

//	@ApiOperation(value = "Adds a user")
//	@RequestMapping(path = "/admin/user", method = RequestMethod.POST)
//	public void addUser(@RequestBody UserForm userForm) throws ApiException {
//		adminDto.add(userForm);
//	}
//	@ApiOperation(value = "Deletes a user")
//	@RequestMapping(path = "/api/admin/user/{id}", method = RequestMethod.DELETE)
//	public void deleteUser(@PathVariable int id) {
//		adminDto.delete(id);
//	}

	@ApiOperation(value = "Gets list of all users")
	@RequestMapping(path = "/admin/user", method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		return adminDto.getAll();
	}


//	@ApiOperation(value = "Get user by id")
//	@RequestMapping(path = "/api/admin/user/{id}", method = RequestMethod.GET)
//	public UserData get() {
//		return adminDto.getById();
//	}
}
