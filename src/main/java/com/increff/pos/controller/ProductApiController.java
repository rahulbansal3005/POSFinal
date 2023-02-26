package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.Data.ProductData;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(value = "/api")
public class ProductApiController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm productForm) throws ApiException {
        dto.add(productForm);
    }

//    @ApiOperation(value = "Deletes a product")
//    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Integer id) {
//        dto.delete(id);
//    }

    @ApiOperation(value = "Gets a product by ID")
    @RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/product", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return dto.getAll();
    }
    @ApiOperation(value = "Updates a product")
    @RequestMapping(path = "/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm productForm) throws ApiException {
        dto.update(id, productForm);
    }

    @ApiOperation(value = "Adds Product in bulk")
    @RequestMapping(path = "/product-bulk", method = RequestMethod.POST)
    public void addBulk(@RequestBody List<ProductForm> productForms) throws ApiException {
        dto.addBulk(productForms);
    }
}
