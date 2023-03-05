package com.increff.pos.controller;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.TestHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PdfControllerTest extends AbstractUnitTest {

    @Autowired
    private PdfApiController pdfApiController;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private OrderApiController orderApiController;

    private List<OrderItem>  orderItem() throws ApiException {
        List<OrderItem> orderItemList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            BrandPojo brandPojo= TestHelper.addBrandToPojo("brandd"+i,"catt"+i);
            brandDao.insert(brandPojo);
            Integer id=brandPojo.getId();

            ProductPojo productPojo=TestHelper.returnProductPojo("barr"+i,"namee"+i,891.52,id);
            productDao.insert(productPojo);
            Integer pid=productPojo.getId();

            inventoryDao.insert(TestHelper.returnInventoryPojo(pid,236+i));
        }
        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setSellingPrice(89.4+i);
            orderItem.setBarCode("barr"+i);
            orderItem.setQuantity(56+i);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
    @Test
    public void testGet() throws ApiException, IOException {
        List<OrderItem> list=orderItem();
        orderApiController.add(list);
        Integer id = orderApiController.getAll().get(0).getId();
        System.out.println(id);
        pdfApiController.get(id);
//        try{
//
//        }
//        catch(ApiException e)
//        {
//            assertEquals("Unable to generate PDF Invoice",e.getMessage());
//        }
    }

    @Test
    public void testDownload() throws IOException, ApiException {
        List<OrderItem> list=orderItem();
        orderApiController.add(list);
        Integer id = orderApiController.getAll().get(0).getId();
        System.out.println(id);
        pdfApiController.get(id);
        ResponseEntity<byte[]> entity=pdfApiController.download(1001);
        System.out.println(entity.toString());
        assertNotEquals(0,entity.toString().length());
    }
}
