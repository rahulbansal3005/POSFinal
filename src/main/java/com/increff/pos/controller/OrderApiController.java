package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.Data.OrderData;
import com.increff.pos.model.Data.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api
@RestController
@RequestMapping(value = "/api")
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Create one Order")
    @RequestMapping(path = "/order", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItem> orderForm) throws ApiException {
        orderDto.add(orderForm);
    }

    @ApiOperation(value = "Gets an Order by ID")
    @RequestMapping(path = "/order/{id}", method = RequestMethod.GET)
    public OrderData getOrder(@PathVariable int id) throws ApiException {
        return orderDto.getOrder(id);
    }

    @ApiOperation(value = "Gets list of all the Orders")
    @RequestMapping(path = "/order", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return orderDto.getAll();
    }

}
