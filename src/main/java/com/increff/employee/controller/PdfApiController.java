package com.increff.employee.controller;

import com.increff.employee.dto.PdfDto;
import com.increff.employee.model.Data.PdfData;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.Invoice.PDF_Generator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class PdfApiController {

    @Autowired
    private PdfDto dto;


    @ApiOperation(value = "Generates PDF")
    @RequestMapping(path = "/api/pdf/{id}", method = RequestMethod.GET)
    public void get(@RequestBody int id) throws ApiException {
        PDF_Generator pdf_generator= new PDF_Generator();
        PdfData d = dto.get(id);
        pdf_generator.pdf_generator(d);
    }


}
