package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dto.TestHelper;
import com.increff.pos.model.Data.BrandData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandContollerTest extends AbstractUnitTest {

    @Autowired
    private BrandApiController brandApiController;

    @Autowired
    private BrandDao brandDao;

    @Test
    public void testAdd() throws ApiException {
        brandApiController.add(TestHelper.brandForm("  puma "," shoes"));
        BrandPojo brandPojo = brandDao.selectAll().get(0);
        assertEquals("puma", brandPojo.getBrand());
        assertEquals("shoes", brandPojo.getCategory());




//        brandApiController.add(TestHelper.brandForm("puma","shoes"));
        try{
            brandApiController.add(TestHelper.brandForm("puma","shoes"));
        }
        catch (ApiException e)
        {
            assertEquals("Brand-Category already existed",e.getMessage());
        }


        try{
            brandApiController.add(TestHelper.brandForm("","shoes"));
        }
        catch (ApiException e)
        {
            assertEquals("Brand name cannot be null or empty",e.getMessage());
        }

        try{
            brandApiController.add(TestHelper.brandForm("puma",""));
        }
        catch (ApiException e)
        {
            assertEquals("Category name cannot be null or empty",e.getMessage());
        }

    }

    @Test
    public void testUpdate() throws ApiException {
        brandApiController.add(TestHelper.brandForm("  puma "," shoes"));
        BrandPojo brandPojo = brandDao.selectAll().get(0);

        brandApiController.update(brandPojo.getId(),TestHelper.brandForm("reebok","shoes"));

    }


//    @Test
//    public void testDelete() throws ApiException {
//        brandApiController.add(TestHelper.brandForm("puma","shoes"));
//        brandApiController.add(TestHelper.brandForm("puma","shirt"));
//        brandApiController.add(TestHelper.brandForm("reebok","shoes"));
//        brandApiController.add(TestHelper.brandForm("adidas","tshirt"));
//
//
//        brandApiController.delete(1);
//
//    }


    @Test
    public void testGet() throws ApiException {

        for(int i=0;i<10;i++)
        {
            brandApiController.add(TestHelper.brandForm("brand"+i, "cat"+i));
        }

        try{
            BrandData brandData=brandApiController.get(10);
        }
        catch (ApiException e)
        {
            assertEquals("Brand with given ID does not exist, id: ",e.getMessage() );
        }

//        BrandData brandData=brandApiController.get(1);
//        assertEquals("brand0", brandData.getBrand());
//        assertEquals("cat0", brandData.getCategory());


        List<BrandData> brandDataList=brandApiController.getAll();
        assertEquals(10,brandDataList.size());



    }


    @Test
    public void testGetAllUniqueBrands() throws ApiException {
        for(int i=0;i<10;i++)
        {
            brandApiController.add(TestHelper.brandForm("brand"+i, "cat"+i));
        }

        for(int i=0;i<5;i++)
        {
            brandApiController.add(TestHelper.brandForm("brand"+i, "cat"));
        }

    }



    @Test
    public void testAddBulk() throws ApiException {
        List<BrandForm> brandForms=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            brandForms.add(TestHelper.brandForm("brand"+i, "cat"+i));
        }

        brandApiController.addInBulk(brandForms);
        assertEquals(10,brandForms.size());

    }
}
