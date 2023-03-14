package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.DailySalesDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.Data.DailySalesData;
import com.increff.pos.model.Data.InventoryReportData;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.model.Data.SalesReportData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.TestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ReportControllerTest extends AbstractUnitTest {

    @Autowired
    private OrderApiController orderApiController;
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

    @Autowired
    private DailySalesDao dailySalesDao;

    @Test
    public void testBrandReport() throws ApiException {
        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            BrandPojo brandPojo2 = TestHelper.addBrandToPojo("brand" + i, "cat" + 2 * i);
            brandDao.insert(brandPojo);
            brandDao.insert(brandPojo2);
            Integer id = brandPojo.getId();
        }

        List<BrandForm> brandForms;
        try {
            brandForms = reportApiController.searchBrandReport(TestHelper.brandForm("brand1", "cat1"));
            Assert.assertEquals(1, brandForms.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());
        }
        try {
            brandForms = reportApiController.searchBrandReport(TestHelper.brandForm("", "cat2"));
            Assert.assertEquals(2, brandForms.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());

        }
        try {
            brandForms = reportApiController.searchBrandReport(TestHelper.brandForm("brand1", ""));
            Assert.assertEquals(2, brandForms.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());

        }
        try {
            brandForms = reportApiController.searchBrandReport(TestHelper.brandForm("", ""));
            Assert.assertEquals(20, brandForms.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());
        }


    }

    @Test
    public void testInventoryReport() throws ApiException {

        for (int i = 0; i < 10; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 89.52, id);
            productDao.insert(productPojo);
            Integer pid = productPojo.getId();
            inventoryDao.insert(TestHelper.returnInventoryPojo(pid, 230 + i));
        }

        List<InventoryReportData> inventoryReportDataList;
        try {
            inventoryReportDataList = reportApiController.searchInventoryReport(TestHelper.brandForm("brand1", "cat1"));
            Assert.assertEquals(1, inventoryReportDataList.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());
        }

        try {
            inventoryReportDataList = reportApiController.searchInventoryReport(TestHelper.brandForm("brand120", "cat132"));
            Assert.assertEquals(0, inventoryReportDataList.size());
        } catch (ApiException e) {
            Assert.assertEquals("Brand-Category Pair does not exist", e.getMessage());
        }
    }

    void Solve() throws ApiException {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BrandPojo brandPojo = TestHelper.addBrandToPojo("brand" + i, "cat" + i);
            brandDao.insert(brandPojo);
            Integer id = brandPojo.getId();

            ProductPojo productPojo = TestHelper.returnProductPojo("bar" + i, "name" + i, 891.52, id);
            productDao.insert(productPojo);
            Integer pid = productPojo.getId();

            inventoryDao.insert(TestHelper.addInventoryPojo(500 + i, pid));
        }
        for (int i = 0; i < 10; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSellingPrice(85.4 + i);
            orderItem.setBarCode("bar" + i);
            orderItem.setQuantity(206 + i);
            orderItemList.add(orderItem);
        }
        orderApiController.add(orderItemList);

        orderItemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSellingPrice(85.4 + i);
            orderItem.setBarCode("bar" + 2 * i);
            orderItem.setQuantity(56 + i);
            orderItemList.add(orderItem);
        }
        orderApiController.add(orderItemList);


        orderItemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSellingPrice(85.4 + i);
            orderItem.setBarCode("bar" + 3 * i);
            orderItem.setQuantity(26 + i);
            orderItemList.add(orderItem);
        }
        orderApiController.add(orderItemList);
    }

//    @Test
//    public void testSalesReport() throws ApiException {
//
//        Solve();
//
//        SalesReportForm salesReportForm = new SalesReportForm();
//        salesReportForm.setBrand("");
//        salesReportForm.setCategory("");
//        salesReportForm.setStartDate("");
//        salesReportForm.setEndDate("");
//
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
////            Assert.assertNotEquals(0,list.size());
//        } catch (ApiException e) {
//            Assert.assertEquals("Start Date is empty", e.getMessage());
//        }
//
//        salesReportForm.setStartDate("2023-02-20");
//
//
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//            Assert.assertNotEquals(0, list.size());
//        } catch (ApiException e) {
//            Assert.assertEquals("End Date is empty", e.getMessage());
//        }
//
//
//        salesReportForm.setEndDate("2023-03-06");
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//            Assert.assertNotEquals(0, list.size());
//        } catch (ApiException e) {
////            Assert.assertEquals("End Date is empty",e.getMessage());
//        }
//
//        salesReportForm.setBrand("brand1");
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//            Assert.assertNotEquals(0, list.size());
//
//        } catch (ApiException e) {
////            Assert.assertEquals("Start Date should be before End Date",e.getMessage());
//        }
//
//
//        salesReportForm.setCategory("cat1");
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//            Assert.assertNotEquals(0, list.size());
//
//        } catch (ApiException e) {
////            Assert.assertEquals("Start Date should be before End Date",e.getMessage());
//        }
//
//
//        salesReportForm.setBrand("");
//
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//            Assert.assertNotEquals(0, list.size());
//
//        } catch (ApiException e) {
////            Assert.assertEquals("Start Date should be before End Date",e.getMessage());
//        }
//
//        salesReportForm.setEndDate("2023-02-20");
//        salesReportForm.setStartDate("2023-03-06");
//        try {
//            List<SalesReportData> list = reportApiController.getSalesReport(salesReportForm);
//        } catch (ApiException e) {
//            Assert.assertEquals("Start Date should be before End Date", e.getMessage());
//        }
//
//
//    }

    @Test
    public void testDailySalesReport() throws ApiException {
        Solve();
        reportApiController.scheduleNow();
        List<SalesPojo> list = dailySalesDao.selectAll();
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());

    }

    @Test
    public void testDailySalesReport2() throws ApiException {
        Solve();
        List<DailySalesData> list= reportApiController.getDailySales();

        System.out.println(list.size());
        Assert.assertEquals(0,list.size());
    }

}
