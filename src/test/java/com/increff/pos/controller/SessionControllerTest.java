package com.increff.pos.controller;

import com.increff.pos.model.Form.LoginForm;
import com.increff.pos.model.Form.SignupForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;


import static org.junit.Assert.assertEquals;

public class SessionControllerTest extends AbstractUnitTest {
    @Autowired
    private SessionController sessionController;


    @Test
    public void testsignup1(){
        SignupForm signupForm=new SignupForm();
        signupForm.setEmail("abc@gmil.com");
        signupForm.setPassword("123erwtg");

        try{
            ModelAndView modelAndView=sessionController.signup(signupForm);
            assertEquals("redirect:/site/login",modelAndView.getViewName());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testsignup2() throws ApiException {
        SignupForm signupForm=new SignupForm();
        signupForm.setEmail("abcd@gmil.com");
        signupForm.setPassword("123erwtgds");

        SignupForm signupForm2=new SignupForm();
        signupForm2.setEmail("abcd@gmil.com");
        signupForm2.setPassword("123erwtgds");

        sessionController.signup(signupForm);
        try{
            sessionController.signup(signupForm2);
        } catch (ApiException e) {
            assertEquals("User with given email already exists",e.getMessage());
        }
    }

    @Test
    public void testsignup3(){
        SignupForm signupForm=new SignupForm();
        signupForm.setPassword("123erwtssg");

        try{
            sessionController.signup(signupForm);
        } catch (ApiException e) {
            assertEquals("Email field is empty",e.getMessage());
        }
    }

    @Test
    public void testsignup4(){
        SignupForm signupForm=new SignupForm();
        signupForm.setEmail("abc@gmil.com");

        try{
            sessionController.signup(signupForm);
        } catch (ApiException e) {
            assertEquals("Password field is empty",e.getMessage());
        }
    }

    @Test
    public void testLogin(){
        MockHttpServletRequest mockHttpServletRequest=new MockHttpServletRequest();
        LoginForm loginForm=new LoginForm();
        loginForm.setEmail("abc@gmil.com");
        loginForm.setPassword("123erwtg");

        try{
            ModelAndView modelAndView=sessionController.login(mockHttpServletRequest,loginForm);
            assertEquals("redirect:/site/login",modelAndView.getViewName());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testLogin2() throws ApiException {
        SignupForm signupForm=new SignupForm();
        signupForm.setEmail("abc@gmil.com");
        signupForm.setPassword("123erwtg");
        sessionController.signup(signupForm);


        MockHttpServletRequest mockHttpServletRequest=new MockHttpServletRequest();
        LoginForm loginForm=new LoginForm();
        loginForm.setEmail("abc@gmil.com");
        loginForm.setPassword("123erwtg");

        try{
            ModelAndView modelAndView=sessionController.login(mockHttpServletRequest,loginForm);
            assertEquals("redirect:/ui/home",modelAndView.getViewName());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testLogout(){
        MockHttpServletRequest mockHttpServletRequest=new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse=new MockHttpServletResponse();
        ModelAndView modelAndView = sessionController.logout(mockHttpServletRequest, mockHttpServletResponse);
        assertEquals("redirect:/site/logout", modelAndView.getViewName());
    }
}
