package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.service.TestHelper;
import com.increff.pos.model.Data.InventoryReportData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportControllerTest extends AbstractUnitTest {

    @Autowired
    private ReportApiController reportApiController;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private InventoryDao inventoryDao;
//    public void addItems() throws ApiException {
//
//    }


    @Test
    public void testBrandReport() throws ApiException {
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            BrandPojo brandPojo2=TestHelper.addBrandToPojo("brand"+i,"cat"+2*i);
            brandDao.insert(brandPojo);
            brandDao.insert(brandPojo2);
            Integer id=brandPojo.getId();
        }

        List<BrandForm> brandForms;
        try{
            brandForms= reportApiController.searchBrandReport(TestHelper.brandForm("brand1","cat1"));
            Assert.assertEquals(1,brandForms.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());
        }
        try{
            brandForms= reportApiController.searchBrandReport(TestHelper.brandForm("","cat2"));
            Assert.assertEquals(2,brandForms.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());

        }
        try{
            brandForms=reportApiController.searchBrandReport(TestHelper.brandForm("brand1",""));
            Assert.assertEquals(2,brandForms.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());

        }
        try{
            brandForms=reportApiController.searchBrandReport(TestHelper.brandForm("",""));
            Assert.assertEquals(20,brandForms.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());
        }


    }

    @Test
    public void testInventoryReport() throws ApiException {

        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);
            Integer pid=productPojo.getId();
            inventoryDao.insert(TestHelper.returnInventoryPojo(pid,230+i));
        }

        List<InventoryReportData> inventoryReportDataList;
        try{
            inventoryReportDataList= reportApiController.searchInventoryReport(TestHelper.brandForm("brand1","cat1"));
            Assert.assertEquals(1,inventoryReportDataList.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());
        }

        try{
            inventoryReportDataList= reportApiController.searchInventoryReport(TestHelper.brandForm("brand120","cat132"));
            Assert.assertEquals(0,inventoryReportDataList.size());
        }
        catch (ApiException e){
            Assert.assertEquals("Brand-Category Pair does not exist",e.getMessage());
        }
    }

    @Test
    public void testSalesReport(){



    }

    @Test
    public void testDailySalesReport(){

    }

}
