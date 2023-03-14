package com.increff.pos.dto;

import com.increff.pos.model.Data.OrderData;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.Validate;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.increff.pos.util.Helper.*;
import static com.increff.pos.util.Validate.checkDuplicateOrderItem;

@Service
public class OrderDto {
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItem> orderForm) throws ApiException {
        List<String> errorMessages = new ArrayList<>();
        for (OrderItem orderItem : orderForm) {
            Validate.checkOrderItem(orderItem, errorMessages);
            checkInventory(orderItem, errorMessages);
        }
//        1) Validate all the Order Items in inventory.
        Validate.ContainDuplicates(orderForm, errorMessages);
        if (errorMessages.size() != 0) {
            String res="";
            int index=1;
            for (String s : errorMessages) {
                System.out.println(s);
                res+=index++ + "). " + s + "\r\n";
            }
            throw new ApiException(res);
        }
        createOrder(orderForm);
    }
    private void createOrder(List<OrderItem> orderForm) throws ApiException {
        // 2) Create new Order.
        OrderPojo orderPojo = new OrderPojo();
        LocalDateTime now = LocalDateTime.now();
        orderPojo.setDate(now);
        orderPojo.setIsInvoiceGenerated(false);
        orderService.addOrder(orderPojo);


        // 3) Add order Items in database.
        List<OrderItemPojo> orderItemPojoList= new ArrayList<OrderItemPojo>();
        Integer orderId=orderPojo.getId();
        for(OrderItem orderItem:orderForm)
        {
            OrderItemPojo orderItemPojo= convertOrderItemToOrderItemPojo(orderItem,orderId);
            orderItemPojo.setProductId(productService.extractProductId(orderItem.getBarCode()));
            orderItemPojoList.add(orderItemPojo);
        }
        orderService.add(orderItemPojoList);


        // 4) Reduce Items from inventory
        for (OrderItem orderItem : orderForm) {
            int productId = productService.extractProductId(orderItem.getBarCode());
            inventoryService.reduceInventory(orderItem,productId);
        }
    }

    private void checkInventory(OrderItem orderItem, List<String> errorMessages) throws ApiException {
        ProductPojo productPojo=productService.getCheck(orderItem.getBarCode());
        if(productPojo==null)
        {
            String error="Product does not exist in Product List: "+ orderItem.getBarCode();
            errorMessages.add(error);
            return;
        }
        if(productPojo.getMrp()<orderItem.getSellingPrice())
        {
            String error="Selling Price is higher than MRP: "+ orderItem.getBarCode();
            errorMessages.add(error);
            return;
        }
        InventoryPojo inventoryPojo=inventoryService.selectOnProdId(productPojo.getId());
        if(inventoryPojo==null)
        {
            String error = "Product with given BarCode: " + orderItem.getBarCode() + " does not exist in inventory ";
            errorMessages.add(error);
            return;
        }
        else if (inventoryPojo.getQuantity()<orderItem.getQuantity()) {
            String error = "Product with given BarCode: " + orderItem.getBarCode() + " does not have sufficient quantity ";
            errorMessages.add(error);
            return;
        }
    }

    public OrderData getOrder(int id) throws ApiException {
        OrderPojo orderPojo = orderService.getOrder(id);
        List<OrderItem> orderItemList = orderService.getOrderItems(orderPojo.getId());
        return convertOrderPojoToOrderData(orderPojo, orderItemList);
    }


    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> list = orderService.getAllOrder();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : list) {
            list2.add(getOrder(orderPojo.getId()));
        }
        return list2;
    }

//    public void addBulk(List<OrderItem> orderForm) throws ApiException {
//        JSONArray array = new JSONArray();
//        Validate.ValidateOrderFormForBulkAdd(orderForm, array);
//        checkInventoryForBulk(orderForm, array);
//        checkDuplicateOrderItem(orderForm,array);
//
//
//        if (array.length() != 0) {
//            throw new ApiException(array.toString());
//        }
//        createOrder(orderForm);
//    }

//    private void checkInventoryForBulk(List<OrderItem> orderItems, JSONArray array){
//        for(OrderItem orderItem:orderItems)
//        {
//            ProductPojo productPojo=productService.getCheck(orderItem.getBarCode());
//            if(productPojo==null)
//            {
//                createOrderErrorobject(orderItem,array);
//            }
//
//            InventoryPojo inventoryPojo=inventoryService.selectOnProdId(productPojo.getId());
//            if(inventoryPojo==null || inventoryPojo.getQuantity()<orderItem.getQuantity())
//            {
//                createOrderErrorobject(orderItem,array);
//            }
//        }
//    }


}
