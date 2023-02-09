package com.increff.pos.dto;

import com.increff.pos.model.Data.InfoData;
import com.increff.pos.model.Form.LoginForm;
import com.increff.pos.model.Form.SignupForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.Helper;
import com.increff.pos.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static com.increff.pos.util.Helper.convertSignupFormToUserPojo;


@Service
public class SessionDto {

    @Autowired
    private UserService sessionService;

    @Autowired
    private InfoData infoData;


    public ModelAndView login(HttpServletRequest httpServletRequest, LoginForm loginForm) throws ApiException {
        UserPojo userPojo = sessionService.get(loginForm.getEmail());
        boolean authenticated = (userPojo != null && Objects.equals(userPojo.getPassword(), loginForm.getPassword()));
        if (!authenticated) {
            infoData.setMessage("Invalid username or password");
            return new ModelAndView("redirect:/site/login");
        }

        // Create authentication object
        Authentication authentication = Helper.convertUserPojoToAuthentication(userPojo);
        // Create new session
        HttpSession session = httpServletRequest.getSession(true);
        // Attach Spring SecurityContext to this new session
        SecurityUtil.createContext(session);
        // Attach Authentication object to the Security Context
        SecurityUtil.setAuthentication(authentication);

        return new ModelAndView("redirect:/ui/home");
    }

    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/site/logout");
    }

    public ModelAndView signup(SignupForm signupForm) throws ApiException {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("emails.properties"));
        } catch (IOException e) {
            System.out.println("Failed to load properties file.");
            e.printStackTrace();
        }
        String email = signupForm.getEmail();
        UserPojo userPojo;
        if (prop.containsKey(email)) {
            userPojo = convertSignupFormToUserPojo(signupForm, "supervisor");
        } else {
            userPojo = convertSignupFormToUserPojo(signupForm, "operator");
        }
        sessionService.add(userPojo);

        return new ModelAndView("redirect:/site/login");
    }
}
