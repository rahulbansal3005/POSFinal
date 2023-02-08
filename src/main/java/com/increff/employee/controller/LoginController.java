package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.Data.InfoData;
import com.increff.employee.model.Form.LoginForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.UserPrincipal;

import io.swagger.annotations.ApiOperation;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private InfoData infoData;
	
	@ApiOperation(value = "Logs in a user")
	@RequestMapping(path = "/session/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView login(HttpServletRequest httpServletRequest, LoginForm loginForm) throws ApiException {
		UserPojo userPojo = userService.get(loginForm.getEmail());
		boolean authenticated = (userPojo != null && Objects.equals(userPojo.getPassword(), loginForm.getPassword()));
		if (!authenticated) {
			infoData.setMessage("Invalid username or password");
			return new ModelAndView("redirect:/site/login");
		}

		// Create authentication object
		Authentication authentication = convert(userPojo);
		// Create new session
		HttpSession session = httpServletRequest.getSession(true);
		// Attach Spring SecurityContext to this new session
		SecurityUtil.createContext(session);
		// Attach Authentication object to the Security Context
		SecurityUtil.setAuthentication(authentication);

		return new ModelAndView("redirect:/ui/home");

	}

	@RequestMapping(path = "/session/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/site/logout");
	}

	private static Authentication convert(UserPojo userPojo) {
		// Create principal
		UserPrincipal principal = new UserPrincipal();
		principal.setEmail(userPojo.getEmail());
		principal.setId(userPojo.getId());

		// Create Authorities
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userPojo.getRole()));
		// you can add more roles if required

		// Create Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null,
				authorities);
		return token;
	}


	@ApiOperation(value = "Sign up a user")
	@RequestMapping(path = "/session/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView signup(HttpServletRequest httpServletRequest, LoginForm loginForm) throws ApiException {


		return new ModelAndView("redirect:/site/login");
	}


}
