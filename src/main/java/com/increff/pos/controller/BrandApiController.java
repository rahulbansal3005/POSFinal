package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.Data.BrandData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api
@RestController
@RequestMapping(value = "/api")
public class BrandApiController {

    @Autowired
    private BrandDto dto;
    @ApiOperation(value = "Adds a Brand")
    @RequestMapping(path = "/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm brandForm) throws ApiException {
        dto.add(brandForm);
    }

//    @ApiOperation(value = "Deletes a Brand")
//    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Integer id) {
//        dto.delete(id);
//    }

    @ApiOperation(value = "Gets a Brand by ID")
    @RequestMapping(path = "/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all Brand")
    @RequestMapping(path = "/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return dto.getAll();
    }


    @ApiOperation(value = "Gets list of unique brands")
    @RequestMapping(path = "/brand/brandNames", method = RequestMethod.GET)
    public List<String> getAllUniqueBrands() throws ApiException {

        return dto.getAllUniqueBrands();
    }

    @ApiOperation(value = "Gets brand category list")
    @RequestMapping(path = "/brand/categ", method = RequestMethod.POST)
    public List<String> getCategory(@RequestBody String brand) throws ApiException {
        return dto.getCategory(brand);
    }

    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody BrandForm brandForm) throws ApiException {
        dto.update(id, brandForm);
    }

    @ApiOperation(value = "Upload brands-category in bulk")
    @RequestMapping(path = "/brand-bulk", method = RequestMethod.POST)
    public void addInBulk(@RequestBody List<BrandForm> brandForms) throws ApiException {
        dto.bulkAdd(brandForms);
    }

}
