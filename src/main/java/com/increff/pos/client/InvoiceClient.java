package com.increff.pos.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.pos.model.Data.PdfData;
import com.increff.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.util.Base64;


@Service
public class InvoiceClient {
    private static final String URL="http://localhost:8000/invoice/api/pdf";

    @Autowired
    private RestTemplate restTemplate;
    public void sendRequestToInvoice(PdfData pdfData,int id) throws ApiException {
        try{
//            RestTemplate restTemplate = new RestTemplate();
            String url = URL;
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
        catch (Exception e)
        {
            throw new ApiException("Unable to generate PDF Invoice");
        }
    }
}
