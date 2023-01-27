package com.increff.employee.dto;

import com.increff.employee.model.Data.OrderData;
import com.increff.employee.model.Data.OrderItem;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.increff.employee.util.helper.*;

@Service
public class OrderDto {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    @Autowired
    InventoryService is;


    @Autowired
    private ProductService ps;
    @Autowired
    private OrderItemDto orderItemDto;

    @Autowired
    private OrderService service;

    public void add(OrderItem[] orderForm) throws ApiException {
//        1) Validate all the Order Items in our inventory.

        List<String> errorMessages=new ArrayList<>();

        for(OrderItem orderItem:orderForm)
        {
            check(orderItem,errorMessages);
        }

        if(errorMessages.size()!=0)
        {
            for(String s:errorMessages)
            {
                System.out.println(s);
            }
            throw new ApiException("Too many errors in Order Item List ");
        }

        // 2) Create new Order.
        OrderPojo orderPojo = convertOrderFormToOrder();
        LocalDateTime now = LocalDateTime.now();
        orderPojo.setTime(dtf.format(now));
        service.addOrder(orderPojo);

        // 3) Add order Items in database.
        service.addOrderItems(orderForm,orderPojo.getId());


        // 4) Reduce Items from inventory

    }
    public boolean check(OrderItem c, List<String>errorMessages) throws ApiException {
        int prod_id= ps.extractProd_Id(c.getBarCode());
        return is.checkQuantity(prod_id,c.getQuantity());
    }

    public OrderData getOrder( int id) throws ApiException {
        OrderPojo p = service.getOrder(id);
        List<OrderItem> c = service.getOrderItems(p.getId());
        return convertOrderPojoToOrderData(p, c);
    }


    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> list = service.getAllOrder();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo p : list) {
            list2.add(getOrder(p.getId()));
        }
        return list2;
    }

}
