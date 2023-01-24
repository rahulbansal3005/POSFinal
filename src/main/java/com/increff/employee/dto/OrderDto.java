package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
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

    public void add(OrderForm orderForm) throws ApiException {
//        1) Validate all the Order Items in our inventory.
        List<String> errorMessages=new ArrayList<>();

        for(OrderItem orderItem:orderForm.getC())
        {
            check(orderItem);
        }

        if(errorMessages.size()!=0)
        {
            return;
        }

        // 2) Create new Order.
        OrderPojo orderPojo = convertOrderFormToOrder();
        LocalDateTime now = LocalDateTime.now();
        orderPojo.setTime(dtf.format(now));
        service.add(orderPojo);

        // 3) Add order Items in database.
        add(orderForm,orderPojo.getId());
    }
    public void add(OrderForm orderForm, int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList= new ArrayList<>();
        for(OrderItem orderItem:orderForm.getC())
        {
            OrderItemPojo orderItemPojo= convertOrderItemToOrderItemPojo(orderItem,orderId);
            orderItemPojo.setProductId(ps.extractProd_Id(orderItem.getBarCode()));
        }
        service.add(orderItemPojoList);
    }
    public boolean check(OrderItem c) throws ApiException {
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
        // List<OrderItemPojo> list1 = service.getAllOrderItems();

        List<OrderData> list2 = new ArrayList<OrderData>();
        // for (OrderPojo p : list) {
        // list2.add(convert(p));
        // }
        // for (OrderItemPojo p : list1) {
        // list2.add(convert(p));
        // }
        for (OrderPojo p : list) {
            list2.add(getOrder(p.getId()));
        }
        return list2;
    }

}
