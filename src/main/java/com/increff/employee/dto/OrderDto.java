package com.increff.employee.dto;

import com.increff.employee.model.Data.OrderData;
import com.increff.employee.model.Data.OrderItem;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.increff.employee.util.Helper.*;

@Service
public class OrderDto {
//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    public void add(OrderItem[] orderForm) throws ApiException {
//        1) Validate all the Order Items in our inventory.
        List<String> errorMessages = new ArrayList<>();

        for (OrderItem orderItem : orderForm) {
            Validate.checkOrderItem(orderItem, errorMessages);
            Validate.ContainDuplicates(orderForm, errorMessages);
            checkInventory(orderItem, errorMessages);
        }

        if (errorMessages.size() != 0) {
            String res="";
            int index=1;
            for (String s : errorMessages) {
                System.out.println(s);
                res+=index++;
                res+="). ";
                res+=s;
                res+="\r\n";
            }
            throw new ApiException(res);
        }


        // 2) Create new Order.
        OrderPojo orderPojo = convertOrderFormToOrder();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + "OrderDTO  now");
        orderPojo.setDate(now);
        orderPojo.setIsInvoiceGenerated(false);
        orderService.addOrder(orderPojo);

        // 3) Add order Items in database.
        orderService.addOrderItems(orderForm, orderPojo.getId());


        // 4) Reduce Items from inventory
        for (OrderItem orderItem : orderForm) {
            int prod_id = productService.extractProd_Id(orderItem.getBarCode());
            inventoryService.reduceInventory(orderItem,prod_id);
        }
    }
    public void checkInventory(OrderItem orderItem, List<String> errorMessages) throws ApiException {
        ProductPojo productPojo=productService.getCheck(orderItem.getBarCode());
        if(productPojo==null)
        {
            String error="Product does not exist in Product List: "+ orderItem.getBarCode();
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

}
