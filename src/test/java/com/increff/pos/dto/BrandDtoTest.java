package com.increff.pos.dto;

import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.service.AbstractUnitTest;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;


public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws Exception {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Brand1");
        brandForm.setCategory("Category1");
        brandDto.add(brandForm);
        Assert.assertEquals(1,brandDto.getAll().size());
    }

}
