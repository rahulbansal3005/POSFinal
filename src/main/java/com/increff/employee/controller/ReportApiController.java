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


    //    BRAND REPORT'S CODE
    @Autowired
    private ReportDto reportDto;

    @ApiOperation(value = "Gets Brand report")
    @RequestMapping(value = "/brand-report", method = RequestMethod.POST)
    public List<BrandForm> searchBrandReport(@RequestBody BrandForm form) throws ApiException {
        return reportDto.getBrandReport(form);
    }

//----------------------------------------------------------------------------------------

    //    INVENTORY REPORT'S CODE
    @ApiOperation(value = "Gets Inventory Report")
    @RequestMapping(value = "/inventory-report", method = RequestMethod.POST)
    public List<InventoryReportData> searchInventoryReport(@RequestBody BrandForm brandForm) throws ApiException {
        List<InventoryReportData> data = reportDto.getInventoryReport(brandForm);
        return data;
    }
//----------------------------------------------------------------------------------------

    //    SALES REPORT CODE
    @ApiOperation(value = "Gets Sales Report")
    @RequestMapping(value = "/sales-report", method = RequestMethod.POST)
    public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm salesReportForm)
            throws ApiException {
        return reportDto.getDateWiseSalesReport(salesReportForm);
    }

//    @ApiOperation(value = "get all sales report")
//    @RequestMapping(path = "/sales-report", method = RequestMethod.GET)
//    public List<SalesReportData> getAll() throws ApiException {
//        return reportDto.getAll();
//    }

//----------------------------------------------------------------------------------------

    //    SCHEDULER'S CODE
    @ApiOperation(value = "Gets list of daily sales report")
    @RequestMapping(path = "/daySales-report", method = RequestMethod.POST)
    public List<DailySalesPojo> getDailySales() throws ApiException {
        return reportDto.getDailySales();
    }


}
