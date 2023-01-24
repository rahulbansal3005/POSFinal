//package com.increff.employee.controller;
//
//import java.util.List;
//
//import com.increff.employee.dto.OrderDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.increff.employee.model.CustomerData;
//import com.increff.employee.model.CustomerForm;
//import com.increff.employee.service.ApiException;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//
//@Api
//@RestController
//public class CustomerApiController {
//
//     @Autowired
//    private OrderDto dto;
//
//    @ApiOperation(value = "Create one Order")
//    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
//    public void add(@RequestBody CustomerForm form) throws ApiException {
//        dto.add(form);
//    }
//    @ApiOperation(value = "Gets an Order by ID")
//    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
//    public CustomerData getOrder(@PathVariable int id) throws ApiException {
//        return dto.getOrder(id);
//    }
//    @ApiOperation(value = "Gets list of all the Orders")
//    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
//    public List<CustomerData> getAll() throws ApiException {
//        return dto.getAll();
//    }
//
//}
