package com.increff.employee.dto;

import com.increff.employee.model.Data.InfoData;
import com.increff.employee.model.Form.LoginForm;
import com.increff.employee.model.Form.SignupForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import com.increff.employee.util.Helper;
import com.increff.employee.util.SecurityUtil;
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


@Service
public class SessionDto {

    @Autowired
    private UserService userService;

    @Autowired
    private InfoData infoData;


    public ModelAndView login(HttpServletRequest httpServletRequest, LoginForm loginForm) throws ApiException {
        UserPojo userPojo = userService.get(loginForm.getEmail());
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

    public ModelAndView signup(SignupForm signupForm) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("emails.properties"));
        } catch (IOException e) {
            System.out.println("Failed to load properties file.");
            e.printStackTrace();
        }
        String email = signupForm.getEmail();
        if (prop.containsKey(email)) {

        } else {

        }

        return new ModelAndView("redirect:/site/login");
    }
}
