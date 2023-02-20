package com.increff.pos.dto;

import com.increff.pos.model.Form.SignupForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SessionDtoTest extends AbstractUnitTest {

    @Autowired
    private SessionDto sessionDto;

    private SignupForm helper(String email,String pass)
    {
        SignupForm signupForm=new SignupForm();
        signupForm.setEmail(email);
        signupForm.setPassword(pass);
        return signupForm;
    }
    @Test
    public void testSignup() throws ApiException {
        SignupForm signupForm=helper("abc@gmail.com","");
        try {
            sessionDto.signup(signupForm);
        }
        catch (ApiException e)
        {
            assertEquals("Password field is empty", e.getMessage());
        }


        SignupForm signupForm1=helper("","123");
        try {
            sessionDto.signup(signupForm1);
        }
        catch (ApiException e)
        {
            assertEquals("Email field is empty", e.getMessage());
        }


        SignupForm signupForm2=helper("abc@gmail.com","123");
            sessionDto.signup(signupForm2);
    }

//    @Test
//    public void testLogin(){
//
//    }
}
