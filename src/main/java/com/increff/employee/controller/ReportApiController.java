package com.increff.employee.controller;


import com.increff.employee.dto.ReportDto;
import com.increff.employee.model.Data.InventoryReportData;
import com.increff.employee.model.Data.SalesReportData;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.SalesReportForm;
import com.increff.employee.pojo.DailySalesPojo;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping(value = "/api")
public class ReportApiController {

    @Autowired
    private ReportDto reportDto;
    @ApiOperation(value = "Gets Brand report")
    @RequestMapping(value = "/brand-report",method = RequestMethod.POST)
    public List<BrandForm> searchBrandReport(@RequestBody BrandForm form){
        return reportDto.getBrandReport(form); }


    @ApiOperation(value = "Gets Inventory Report")
    @RequestMapping(value = "/inventory-report", method = RequestMethod.POST)
    public List<InventoryReportData> searchInventoryReport() throws ApiException {
        List<InventoryReportData> data =  reportDto.getInventoryReport();
        return data;
    }

    @ApiOperation(value = "Gets Sales Report")
    @RequestMapping(value = "/sales-report", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm)
            throws ApiException {
        return reportDto.getSalesReport(salesReportForm);
    }

//    @ApiOperation(value = "Gets list of daily sales report")
//    @RequestMapping(path = "/daySales-report", method = RequestMethod.GET)
//    public List<DailySalesPojo> getDailySales() throws ApiException {
//        return reportDto.getDailySales();
//    }
}
