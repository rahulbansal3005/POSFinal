package com.increff.employee.dto;

import com.increff.employee.model.Data.PdfData;
import com.increff.employee.model.Data.PdfListData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class PdfDto {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
    public PdfData get(int id) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllByOrderId(id);
        if(orderItemPojoList.size()==0)
            throw new ApiException("no order items present in order to place");

        PdfData pdfData = new PdfData();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "OrderDTO  now");
//        orderPojo.setDate(now);
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
        return pdfData;
    }
}
