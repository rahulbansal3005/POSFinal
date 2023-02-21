package com.increff.pos.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.pos.model.Data.PdfData;
import com.increff.pos.model.Data.PdfListData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class PdfDto {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    public void get(int id) throws IOException, ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllByOrderId(id);
        if(orderItemPojoList.size()==0)
            throw new ApiException("no order items present in order to place");

        PdfData pdfData = new PdfData();
        LocalDateTime now = LocalDateTime.now();
        orderService.update(id,now);
        List<PdfListData> pdfListData = new ArrayList<>();
        Integer c = 0;
        Double total = 0.0;
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            c++;
            ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
            total +=Helper.convertOrderItemPojoToPdfData(orderItemPojo,pdfListData,pdfData,c,productPojo);
        }
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = LocalDateTime.now().format(date);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = LocalDateTime.now().format(time);
        pdfData.setInvoiceTime(formattedTime);
        pdfData.setInvoiceDate(formattedDate);
        pdfData.setOrderId(id);
        pdfData.setTotal(total);
        pdfData.setItemList(pdfListData);


        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/invoice/api/pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(pdfData);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        String response = restTemplate.postForObject(url, request, String.class);

        String filePath = "D:/Repos/POS/invoices\\order_"+id+".pdf";
        byte[] decodedBytes = Base64.getDecoder().decode(response);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(decodedBytes);
        fileOutputStream.close();
    }

    public ResponseEntity<byte[]> download(int id) throws ApiException, IOException {
        Path pdf = Paths.get("../invoices/order_" + id + ".pdf");
        byte[] contents = Files.readAllBytes(pdf);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "order_" + id + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
}
