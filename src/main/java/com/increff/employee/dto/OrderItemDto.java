package com.increff.employee.dto;

import com.increff.employee.model.OrderItem;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemDto {

    @Autowired
    InventoryService is;

    @Autowired
    ProductService ps;
    public boolean check(OrderItem c) throws ApiException {
        int prod_id= ps.extractProd_Id(c.getBarCode());
        return is.checkQuantity(prod_id,c.getQuantity());
    }
}
