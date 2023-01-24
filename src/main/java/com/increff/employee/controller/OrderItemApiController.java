package com.increff.employee.controller;


import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderItem;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class OrderItemApiController {
    @Autowired
    OrderItemDto dto;

    @ApiOperation(value = "Checks one OrderItem in the inventory")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
    public boolean add(@RequestBody OrderItem c) throws ApiException {
        return dto.check(c);
    }
    
}
