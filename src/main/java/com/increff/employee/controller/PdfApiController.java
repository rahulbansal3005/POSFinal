package com.increff.employee.controller;

import com.increff.employee.dto.PdfDto;
import com.increff.employee.model.Data.PdfData;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.Invoice.PDF_Generator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api
@RestController
public class PdfApiController {

    @Autowired
    private PdfDto dto;
    @ApiOperation(value = "Generates PDF")
    @RequestMapping(path = "/api/pdf/{id}", method = RequestMethod.GET)
    public void get(@PathVariable int id) throws ApiException {
//        System.out.println("api called 1");
        PDF_Generator pdf_generator = new PDF_Generator();
//        System.out.println("api called 2");
        PdfData pdfData = dto.get(id);
//        System.out.println("api called 3");
        pdf_generator.pdf_generator(pdfData);
//        System.out.println("api called 4");
    }

    @ApiOperation(value = "generate pdf")
    @RequestMapping(path = "/api/pdf/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable int id) throws ApiException, IOException {

        Path pdf = Paths.get("./src/main/resources/apache/PdfFile/" + id + "_invoice.pdf");

        byte[] contents = Files.readAllBytes(pdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "Order" + id + "_invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }

}
