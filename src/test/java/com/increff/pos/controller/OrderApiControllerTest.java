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
import org.apache.xpath.operations.Or;
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

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,891.52,id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar"+i,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(89.4+i);
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

    }

//    @Test
//    public void testAdd2() throws ApiException {
//        List<OrderItem> orderItemList=addItems();
//        orderApiController.addBulk(orderItemList);
//    }

    @Test
    public void testAdd3() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,891.52,id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar"+i,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(89.4+i);
            orderItem.setBarCode("bar"+i);
            orderItem.setQuantity(56+i);
            orderItemList.add(orderItem);
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(89.4+i);
            orderItem.setBarCode("bar"+i);
            orderItem.setQuantity(56+i);
            orderItemList.add(orderItem);
        }
        try {
            orderApiController.add(orderItemList);
        }
        catch (ApiException e)
        {
            String ass="";
            int index=1;

            for(int i=0;i<10;i++){
                ass+=index++ + "). " + "Duplicate product with BarCode bar"+ i +"\r\n";
            }

            Assert.assertEquals(ass,e.getMessage());

        }
    }



    @Test
    public void testAdd4() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,891.52,id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar"+i,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(89.4+i);
            orderItem.setBarCode("barr"+i);
            orderItem.setQuantity(56+i);
            orderItemList.add(orderItem);
        }
        try {
            orderApiController.add(orderItemList);
        }
        catch (ApiException e)
        {
            String ass="";
            int index=1;

            for(int i=0;i<10;i++){
                ass+=index++ + "). " + "Product does not exist in Product List: barr"+ i +"\r\n";
            }

            Assert.assertEquals(ass,e.getMessage());
        }
    }


    @Test
    public void testAdd5() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,891.52,id);
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
        try {
            orderApiController.add(orderItemList);
        }
        catch (ApiException e)
        {
            String ass="";
            int index=1;

            for(int i=0;i<10;i++){
                ass+=index++ + "). " + "Selling Price is higher than MRP: bar"+ i  +"\r\n";
            }

            Assert.assertEquals(ass,e.getMessage());
        }

    }


    @Test
    public void testAdd6() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brand"+i,"cat"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("bar"+i,"name"+i,891.52,id);
            productDao.insert(productPojo);

            inventoryApiController.add(TestHelper.addInventory("bar"+i,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(85.4+i);
            orderItem.setBarCode("bar"+i);
            orderItem.setQuantity(256+i);
            orderItemList.add(orderItem);
        }

        try {
            orderApiController.add(orderItemList);
        }
        catch (ApiException e)
        {
            String ass="";
            int index=1;

            for(int i=0;i<10;i++){
                ass+=index++ + "). " + "Product with given BarCode: bar"+ i +" does not have sufficient quantity " +"\r\n";
            }

            Assert.assertEquals(ass,e.getMessage());
        }
    }
    @Test
    public void testGet() throws ApiException {
        List<OrderItem> orderItemList=addItems();
        orderApiController.add(orderItemList);
        Integer x=5;
        try{
            OrderData orderData=orderApiController.getOrder(1001);
            System.out.println(orderData.getId());
//            x=orderData.getId();
            Assert.assertEquals(10,orderData.getOrderItemList().size());
//            List<OrderData> list=orderApiController.getAll();
//            System.out.println(list.size());
//            x=list.get(0).getId();
//            System.out.println(x);
        }
        catch (ApiException e)
        {
            System.out.println(x);
            Assert.assertEquals("Order with given ID does not exit, id: 1001", e.getMessage());
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
