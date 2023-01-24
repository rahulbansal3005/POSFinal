package com.increff.employee.dto;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import static com.increff.employee.util.helper.convertOrderFormListToOrderItemPojo;
import static com.increff.employee.util.helper.convertOrderItemToOrderItemPojo;

@Service
public class OrderItemDto {



    @Autowired
    OrderItemService service;
    @Autowired
    ProductService ps;







}
