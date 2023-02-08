package com.increff.employee.dto;


import com.increff.employee.model.Data.UserData;
import com.increff.employee.model.Form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import com.increff.employee.util.Helper;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.Normalize.normalize;

@Service
public class AdminDto {
    @Autowired
    private UserService userService;
    public void add(UserForm userForm) throws ApiException {
        Validate.validateUserForm(userForm);
        normalize(userForm);
        UserPojo userPojo = Helper.convertUserFormToUserPojo(userForm);
        userService.add(userPojo);
    }

    public void delete(int id) {
        userService.delete(id);
    }

    public List<UserData> getAll() {
        List<UserPojo> list = userService.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo userPojo : list) {
            list2.add(Helper.convertUserPojoToUserData(userPojo));
        }
        return list2;
    }
}
