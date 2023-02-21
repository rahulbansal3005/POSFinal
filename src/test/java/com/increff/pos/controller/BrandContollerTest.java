package com.increff.pos.controller;

import com.increff.pos.dto.TestHelper;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class BrandContollerTest extends AbstractUnitTest {

    @Autowired
    private BrandApiController brandApiController;

    @Test
    public void testAdd() throws ApiException {

        brandApiController.add(TestHelper.brandForm("puma","shoes"));
        try{
            brandApiController.add(TestHelper.brandForm("puma","shoes"));
        }
        catch (ApiException e)
        {
            assertEquals("Brand-Category already existed",e.getMessage());
        }
    }


    @Test
    public void testDelete() throws ApiException {
        brandApiController.add(TestHelper.brandForm("puma","shoes"));
        brandApiController.add(TestHelper.brandForm("puma","shirt"));
        brandApiController.add(TestHelper.brandForm("reebok","shoes"));
        brandApiController.add(TestHelper.brandForm("adidas","tshirt"));


        brandApiController.delete(1);

    }
}
