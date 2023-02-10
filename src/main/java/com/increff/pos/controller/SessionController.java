package com.increff.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.increff.pos.dto.SessionDto;
import com.increff.pos.model.Form.SignupForm;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.increff.pos.model.Form.LoginForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SessionController {

	@Autowired
	private SessionDto sessionDto;

	@ApiOperation(value = "Logs in a user")
	@RequestMapping(path = "/session/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView login(HttpServletRequest httpServletRequest, LoginForm loginForm) throws ApiException {
		return sessionDto.login(httpServletRequest,loginForm);
	}

	@RequestMapping(path = "/session/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		return sessionDto.logout(request,response);
	}

	@ApiOperation(value = "Signs up a user")
	@RequestMapping(path = "/session/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView signup(SignupForm signupForm) throws ApiException {
		return sessionDto.signup(signupForm);
	}


}
