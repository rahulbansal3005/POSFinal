package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.service.TestHelper;
import com.increff.pos.model.Data.OrderData;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderApiControllerTest extends AbstractUnitTest {


    @Autowired
    private OrderApiController orderApiController;
    @Autowired
    private InventoryApiController inventoryApiController;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;
    public List<OrderItem> addItems() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,89.52,id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar"+i,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(895.4+i);
            orderItem.setBarCode("bar"+i);
            orderItem.setQuantity(56+i);
            orderItemList.add(orderItem);
        }

        return orderItemList;
    }
    @Test
    public void testAdd() throws ApiException {
        List<OrderItem> orderItemList=addItems();

        orderApiController.add(orderItemList);
        orderApiController.addBulk(orderItemList);
    }

    @Test
    public void testGet() throws ApiException {
        List<OrderItem> orderItemList=addItems();
//        for(int i=0;i<orderItemList.size();i++)
//        {
//            System.out.println(orderItemList.get(i).get);
//        }
        orderApiController.add(orderItemList);
        try{
            OrderData orderData=orderApiController.getOrder(0);
            Assert.assertEquals(10,orderData.getOrderItemList().size());
        }
        catch (ApiException e)
        {
            Assert.assertEquals("Order with given ID does not exit, id: 0", e.getMessage());
        }



        try {

            List<OrderData> orderDataList=orderApiController.getAll();
            Assert.assertEquals(10,orderDataList.get(0).getOrderItemList().size());
        }
        catch (ApiException e)
        {

        }
    }

    @Test
    public void testUpdate(){

    }

}
