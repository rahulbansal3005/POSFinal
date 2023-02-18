package com.increff.pos.dto;

import com.increff.pos.model.Data.BrandData;
import com.increff.pos.model.Data.OrderData;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    private OrderDto orderDto;


    @Autowired
    private InventoryDto inventoryDto;

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Test
    public void testAdd() throws ApiException {
        List<OrderItem> orderItems=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            OrderItem orderItem=new OrderItem();
            BrandForm brandForm = new BrandForm();
            brandForm.setBrand("b"+i);
            brandForm.setCategory("c"+i);
            brandDto.add(brandForm);

            ProductForm productForm = new ProductForm();
            productForm.setBarcode("a"+i);
            productForm.setBrand("b"+i);
            productForm.setCategory("c"+i);
            productForm.setName("d"+i);
            productForm.setMrp(1.0);
            productDto.add(productForm);

            InventoryForm inventoryForm = new InventoryForm();
            inventoryForm.setBarcode("a"+i);
            inventoryForm.setQuantity(49+i);
            inventoryDto.add(inventoryForm);

            orderItem.setBarCode("a"+i);
            orderItem.setQuantity(1+i);
            orderItem.setSellingPrice(1.0+i);
            orderItems.add(orderItem);
        }

        try {
            orderDto.add(orderItems);
            orderDto.addBulk(orderItems);
        } catch (ApiException e) {
            assertEquals("Product does not exist in Product List: ", e.getMessage());
//            assertEquals();
        }
        List<OrderData> orderDataList = orderDto.getAll();
        assertEquals(2, orderDataList.size());
    }




}
