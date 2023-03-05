package com.increff.pos.dto;


import com.increff.pos.model.Data.UserData;
import com.increff.pos.model.Form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.Helper;
import com.increff.pos.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.Normalize.normalizeUserForm;

@Service
public class AdminDto {
    @Autowired
    private UserService userService;
//    public void add(UserForm userForm) throws ApiException {
//        Validate.validateUserForm(userForm);
//        normalizeUserForm(userForm);
//        UserPojo userPojo = Helper.convertUserFormToUserPojo(userForm);
//        userService.add(userPojo);
//    }

//    public void delete(int id) {
//        userService.delete(id);
//    }

    public List<UserData> getAll() {
        List<UserPojo> list = userService.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo userPojo : list) {
            list2.add(Helper.convertUserPojoToUserData(userPojo));
        }
        return list2;
    }

//    public UserData getById() {
//        UserData userData=new UserData();
//        return userData;
//    }
}
