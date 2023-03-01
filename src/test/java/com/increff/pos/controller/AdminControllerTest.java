package com.increff.pos.controller;

import com.increff.pos.dao.UserDao;
import com.increff.pos.model.Data.UserData;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminControllerTest extends AbstractUnitTest {
    @Autowired
    private AdminApiController adminApiController;
    @Autowired
    private UserDao userDao;
    @Test
    public void testGet(){
        for(int i=0;i<10;i++)
        {
            UserPojo userPojo=new UserPojo();
            userPojo.setPassword("pass"+i);
            userPojo.setEmail("ers@gmail.com"+i);
            userPojo.setRole("operator");
            userDao.insert(userPojo);
        }

        try{
            List<UserData> list=adminApiController.getAllUser();
            Assert.assertEquals(10,list.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
