package com.increff.employee.dto;

import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import static com.increff.employee.util.helper.convertOrderFormListToOrderItemPojo;


@Service
public class OrderItemDto {



    @Autowired
    OrderItemService service;
    @Autowired
    ProductService ps;







}
