package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.InventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.Data.InventoryData;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(value = "/api")
public class InventoryApiController {

    @Autowired
    InventoryDto dto;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm inventoryForm) throws ApiException {
        dto.add(inventoryForm);
    }

//    @ApiOperation(value = "Deletes an inventory")
//    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Integer id) {
//        dto.delete(id);
//    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all the items in the inventory")
    @RequestMapping(path = "/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an inventory")
    @RequestMapping(path = "/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody InventoryForm inventoryForm) throws ApiException {
        dto.update(id, inventoryForm);
    }

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/inventory-bulk", method = RequestMethod.POST)
    public void addBulk(@RequestBody List<InventoryForm> inventoryForm) throws ApiException {
        dto.addBulk(inventoryForm);
    }



}
