package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.service.TestHelper;
import com.increff.pos.model.Data.ProductData;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ProductControllerTest extends AbstractUnitTest {
    @Autowired
    private ProductApiController productApiController;

    @Autowired
    private BrandApiController brandApiController;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

    @Test
    public void testAdd() throws ApiException {
        brandApiController.add(TestHelper.brandForm("  brand1 "," cat1"));
        productApiController.add(TestHelper.addProduct("bar1","brand1","cat1","name1",89.565));
        ProductPojo productPojo=productDao.selectAll().get(0);

        assertEquals("bar1", productPojo.getBarcode());
        assertEquals("name1", productPojo.getName());

        try{
            productApiController.add(TestHelper.addProduct("bar1","brand1","cat1","name1",89.565));
        }
        catch (ApiException e)
        {
            assertEquals("Barcode already present",e.getMessage());
        }
    }
    @Test
    public void testAdd2(){
        try{
            productApiController.add(TestHelper.addProduct("bar1","brand1","cat1","name1",-89.565));
        }
        catch (ApiException e)
        {
            assertEquals("MRP cannot be negative",e.getMessage());
        }
    }

    @Test
    public void testAdd3(){
        try{
            productApiController.add(TestHelper.addProduct("bar1","brand1","cat1","",89.565));
        }
        catch (ApiException e)
        {
            assertEquals("Name cannot be empty or null",e.getMessage());
        }
    }
    @Test
    public void testAdd4(){
        try{
            productApiController.add(TestHelper.addProduct("bar1","brand1","","name1",89.565));
        }
        catch (ApiException e)
        {
            assertEquals("Category cannot be null or empty",e.getMessage());
        }
    }
    @Test
    public void testAdd5(){
        try{
            productApiController.add(TestHelper.addProduct("bar1","","cat1","name1",89.565));
        }
        catch (ApiException e)
        {
            assertEquals("Brand cannot be empty or null",e.getMessage());
        }
    }

    @Test
    public void testAdd6(){
        try{
            productApiController.add(TestHelper.addProduct("","brand1","cat1","name1",89.565));
        }
        catch (ApiException e)
        {
            assertEquals("Barcode cannot be empty or null",e.getMessage());
        }
    }
    @Test
    public void testGet() throws ApiException,NullPointerException {
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);
        }

        try{
            ProductData productData=productApiController.get(0);
            assertEquals("name0",productData.getName());
        }
        catch (ApiException e)
        {
            assertEquals("Product with given ID does not exist, id: 0",e.getMessage());
        }
    }


    @Test
    public void testGet2() throws ApiException {
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);
        }
        try{
            ProductData productData=productApiController.get(1);
            assertEquals("name0",productData.getName());
        }
        catch (ApiException e)
        {
            assertEquals("Product with given ID does not exist, id: 1",e.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);
        }
        List<ProductData> productDataList=productApiController.getAll();
        assertEquals(10,productDataList.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        Integer id2=0;
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);
            id2=productPojo.getId();
//            System.out.println(id2);
        }


        try{
            productApiController.update(100,TestHelper.addProduct("bar123","brand1","cat1","name1",56.23));
        }
        catch (ApiException e)
        {
            assertEquals("Product with given ID does not exist, id: 100",e.getMessage());
        }

        try{
            productApiController.update(id2,TestHelper.addProduct("bar123","brand1","cat1","name1",56.23));
        }
        catch (ApiException e)
        {
            assertEquals("Product with given ID does not exist, id: "+id2,e.getMessage());
        }
    }

    @Test
    public void testBulk() throws ApiException {
        List<ProductForm> productFormList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();
            productFormList.add(TestHelper.addProduct("bar"+i,"brand"+i,"cat"+i,"name"+i,89.3+i));
        }

        productApiController.addBulk(productFormList);
    }

    @Test
    public void testBulk2() throws ApiException {
        List<ProductForm> productFormList=new ArrayList<>();
        for(int i=0;i<6000;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();
            productFormList.add(TestHelper.addProduct("bar"+i,"brand"+i,"cat"+i,"name"+i,89.3+i));
        }
        try{
            productApiController.addBulk(productFormList);

        }
        catch (ApiException e)
        {
            assertEquals("File is larger than 5000",e.getMessage());
        }
    }

    @Test
    public void testBulk3() throws ApiException {
        List<ProductForm> productFormList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo=TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();
            productFormList.add(TestHelper.addProduct("bar"+i,"brand"+i,"cat"+i,"name"+i,89.3+i));
        }

        productFormList.add(TestHelper.addProduct("bar0","brand0","cat0","name0",89.3));
        productFormList.add(TestHelper.addProduct("bar1","brand1","cat1","name1",89.3));

        try{
            productApiController.addBulk(productFormList);
        }
        catch (ApiException e){
            assertNotEquals(0,e.getMessage().length());
        }
    }

}
