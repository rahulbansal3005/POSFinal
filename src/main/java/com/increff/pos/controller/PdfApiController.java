package com.increff.pos.controller;

import com.increff.pos.dto.PdfDto;
import com.increff.pos.model.Data.PdfData;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.Invoice.PDF_Generator;
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
@RequestMapping(value = "/api")
public class PdfApiController {

    @Autowired
    private PdfDto dto;

    @ApiOperation(value = "Generates PDF")
    @RequestMapping(path = "/pdf/{id}", method = RequestMethod.GET)
    public void get(@PathVariable int id) throws ApiException, IOException {
        dto.get(id);
    }

    @ApiOperation(value = "generate pdf")
    @RequestMapping(path = "/pdf/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable int id) throws ApiException, IOException {
        return dto.download(id);
    }

}
