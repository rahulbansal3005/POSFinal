package com.increff.employee.dto;

import com.increff.employee.model.Customer;
import com.increff.employee.model.CustomerData;
import com.increff.employee.model.CustomerForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.CustomerService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderDto {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private ProductService ps;


    @Autowired
    private CustomerService service;

    public void add(CustomerForm form) throws ApiException {
        HashMap<String, Integer> errors = new HashMap<String, Integer>();
        service.findingError(form, errors);
        for (Map.Entry<String, Integer> mapElement : errors.entrySet()) {
            int val = mapElement.getValue();
            String key = mapElement.getKey();
            if (val > 0) {
                System.out.println("Quantity not sufficient in the inventory" + key + val);
            } else {
                System.out.println("Barcode does not exist in the inventory" + key);
            }
        }

        OrderPojo p1 = convertFormToOrder();
        service.add(p1);
        List<OrderItemPojo> p2 = convertFormToOrderItem(form, p1.getId());
        service.add(p2);
    }


    public CustomerData getOrder( int id) throws ApiException {
        OrderPojo p = service.getOrder(id);
        List<Customer> c = service.getOrderItems(p.getId());
        return convert(p, c);
    }


    public List<CustomerData> getAll() throws ApiException {
        List<OrderPojo> list = service.getAllOrder();
        // List<OrderItemPojo> list1 = service.getAllOrderItems();

        List<CustomerData> list2 = new ArrayList<CustomerData>();
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




    private List<OrderItemPojo> convertFormToOrderItem(CustomerForm f, int orderId) throws ApiException {
        List<OrderItemPojo> list = new ArrayList<OrderItemPojo>();
        for (Customer orderItemForm : f.getC()) {
            OrderItemPojo p = new OrderItemPojo();
            p.setOrderId(orderId);
            p.setProductId(ps.extractProd_Id(orderItemForm.getBarCode()));
            p.setQuantity(orderItemForm.getQuantity());
            p.setSellingPrice(orderItemForm.getMrp());
            list.add(p);
        }
        return list;
    }

    private OrderPojo convertFormToOrder() throws ApiException {
        LocalDateTime now = LocalDateTime.now();
        OrderPojo p = new OrderPojo();
        p.setTime(dtf.format(now));
        return p;
    }

    private CustomerData convert(OrderPojo p, List<Customer> c) {
        CustomerData d = new CustomerData();
        d.setId(p.getId());
        d.setDateTime(p.getTime());
        d.setC(c);
        return d;
    }


    // @ApiOperation(value = "Gets list of all Orders")
    // @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    // public List<CustomerData> getA() {
    // List<OrderPojo> list = service.getAll();
    // List<OrderItemPojo> list1 = service.getAll();

    // List<CustomerData> list2 = new ArrayList<CustomerData>();
    // for (OrderPojo p : list) {
    // list2.add(convert(p));
    // }
    // for (OrderItemPojo p : list1) {
    // list2.add(convert(p));
    // }
    // return list2;
    // }

    // @ApiOperation(value = "Gets list of all Order Items")
    // @RequestMapping(path = "/api/orderItem", method = RequestMethod.GET)
    // public List<OrderItemData> getAllOrderItem {
    // List<CustomerPojo> list = service.getAll();
    // List<CustomerData> list2 = new ArrayList<CustomerData>();
    // for (CustomerPojo p : list) {
    // list2.add(convert(p));
    // }
    // return list2;
    // }

    // @ApiOperation(value = "Updates a Customer Order")
    // @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    // public void update(@PathVariable int id, @RequestBody CustomerForm f) throws
    // ApiException {
    // CustomerPojo p = convert(f);
    // service.update(id, p);
    // }

    // private static CustomerData convert(CustomerPojo p) {
    // CustomerData d = new CustomerData();
    // d.setId(p.getId());
    // d.setBarcode(p.getBarcode());
    // d.setBrand_category(p.getBrand_category());
    // d.setName(p.getName());
    // d.setMrp(p.getMrp());
    // return d;
    // }

    // private CustomerPojo convert(CustomerForm f) throws ApiException {
    // CustomerPojo p = new CustomerPojo();
    // p.setBarcode(f.getBarcode());
    // p.setBrand_category(bs.extractId(f));
    // p.setName(f.getName());
    // p.setMrp(f.getMrp());
    // return p;
    // }



    // private static CustomerData convert2 (CustomerForm f)
    // {
    // CustomerData d = new CustomerData();

    // return d;

    // }

}
