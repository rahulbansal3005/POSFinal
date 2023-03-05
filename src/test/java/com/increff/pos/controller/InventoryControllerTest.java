package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.Data.InventoryData;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.TestHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class InventoryControllerTest extends AbstractUnitTest {
    @Autowired
    private InventoryApiController inventoryApiController;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private InventoryDao inventoryDao;


    public void addItems() throws ApiException {
        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar" + i, 236 + i));
        }
    }

    @Test
    public void testAdd() throws Exception {
        addItems();
        InventoryPojo inventoryPojo = inventoryDao.selectAll().get(0);

        assertEquals(new Integer(236), inventoryPojo.getQuantity());
        assertEquals(new Integer(1), inventoryPojo.getProductId());
        List<InventoryPojo> inventoryPojoList = inventoryDao.selectAll();
        for (int i = 0; i < inventoryPojoList.size(); i++) {
            System.out.println(inventoryPojoList.get(i).getId() + " in");
        }


        try {
            inventoryApiController.add(TestHelper.addInventory("", 565));
        } catch (ApiException e) {
            assertEquals("BarCode is Null or Empty", e.getMessage());
        }


        try {
            inventoryApiController.add(TestHelper.addInventory("bar1", -89));
        } catch (ApiException e) {
            assertEquals("Entered Quantity is negative", e.getMessage());
        }


        try {
            inventoryApiController.add(TestHelper.addInventory("bar1", 565));
            InventoryPojo inventoryPojo1 = inventoryDao.selectAll().get(1);

            assertEquals(new Integer(802), inventoryPojo1.getQuantity());
        } catch (ApiException e) {
            assertEquals("BarCode is Null or Empty", e.getMessage());
        }


    }

    @Test
    public void testAdd2() throws Exception {
        BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" , "cat");
        brandDao.insert(brandPojo);
        Integer id = brandPojo.getId();

        ProductPojo productPojo = TestHelper.returnProductPojo("bar", "name", 89.52, id);
        productDao.insert(productPojo);

        try {
            inventoryApiController.add(TestHelper.addInventory("barr" , 236));
        } catch (ApiException e) {
            assertEquals("Product does not exist in the Product List", e.getMessage());
        }
    }


    @Test
    public void testUpdate() throws ApiException {
        addItems();
        try {
            inventoryApiController.update(2, TestHelper.addInventory("bar1", 895));
            InventoryPojo inventoryPojo1 = inventoryDao.selectAll().get(1);
            List<InventoryPojo> inventoryPojo = inventoryDao.selectAll();
            for (int i = 0; i < inventoryPojo.size(); i++) {
                System.out.println(inventoryPojo.get(i).getQuantity());
            }
            assertEquals(new Integer(895), inventoryPojo1.getQuantity());
        } catch (ApiException e) {
            assertEquals("Inventory with given ID does not exist, id: 2", e.getMessage());
        }
    }


    @Test
    public void testGet() throws ApiException {
        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar" + i, 236 + i));
            InventoryPojo inventoryPojo = inventoryDao.selectAll().get(0);
        }

        try {
            InventoryData inventoryData = inventoryApiController.get(1);
            assertEquals("bar0", inventoryData.getBarcode());
        } catch (ApiException e) {
            assertEquals("Inventory with given ID does not exist, id: 1", e.getMessage());
        }


        try {
            InventoryData inventoryData = inventoryApiController.get(10);
            assertEquals("bar9", inventoryData.getBarcode());
        } catch (ApiException e) {
            assertEquals("Inventory with given ID does not exist, id: 10", e.getMessage());
        }

        try {
            InventoryData inventoryData = inventoryApiController.get(110);
//            assertEquals("bar110",inventoryData.getBarcode());
        } catch (ApiException e) {
            assertEquals("Inventory with given ID does not exist, id: 110", e.getMessage());
        }


        List<InventoryData> inventoryData = inventoryApiController.getAll();
        assertEquals(10, inventoryData.size());
    }

    @Test
    public void testBulk() throws ApiException {
        List<InventoryForm> inventoryForms = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);

            inventoryForms.add(TestHelper.addInventory("bar" + i, 236 + i));
        }
        inventoryApiController.addBulk(inventoryForms);

    }


    @Test
    public void testBulk2() throws ApiException {
        List<InventoryForm> inventoryForms = new ArrayList<>();
        for (int i = 0; i < 6000; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);

            inventoryForms.add(TestHelper.addInventory("bar" + i, 236 + i));
        }

        try {
            inventoryApiController.addBulk(inventoryForms);
        } catch (ApiException e) {
            assertEquals("File size is larger than 5000", e.getMessage());
        }
    }

    @Test
    public void testBulk3() throws ApiException {
        List<InventoryForm> inventoryForms = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);

            inventoryForms.add(TestHelper.addInventory("bar" + i, 236 + i));
        }

        try {
            inventoryApiController.addBulk(inventoryForms);
        } catch (ApiException e) {
            assertNotEquals(0, e.getMessage().length());
        }
    }
}
