package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.Customer;
import com.increff.employee.model.CustomerData;
import com.increff.employee.model.CustomerForm;
// import com.increff.employee.model.OrderData;
// import com.increff.employee.model.OrderItemData;
// import com.increff.employee.pojo.CustomerPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.CustomerService;
// import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
// import com.increff.employee.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Api
@RestController
public class CustomerApiController {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    // LocalDateTime now = LocalDateTime.now();

    @Autowired
    private ProductService ps;

    // @Autowired
    // private InventoryService is;

    @Autowired
    private CustomerService service;

    @ApiOperation(value = "Adds a Customer Order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add(@RequestBody CustomerForm form) throws ApiException {
        HashMap<String, Integer> errors = new HashMap<String, Integer>();
        service.findingError(form, errors);

        // List<String> error = new ArrayList<String>();
        // service.findingError(form, error);
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

    // @ApiOperation(value = "Deletes a Customer Order")
    // @RequestMapping(path = "/api/customer/{id}", method = RequestMethod.DELETE)
    // /api/1
    // public void delete(@PathVariable int id) {
    // service.delete(id);
    // }

    @ApiOperation(value = "Gets an Order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public CustomerData getOrder(@PathVariable int id) throws ApiException {
        OrderPojo p = service.getOrder(id);
        List<Customer> c = service.getOrderItems(p.getId());
        return convert(p, c);
    }

    // @ApiOperation(value = "Gets an OrderItem by ID")
    // @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
    // public CustomerData getOrderItems(@PathVariable int id) throws ApiException {
    // OrderItemPojo p = service.getOrderItem(id);
    // return convert(p);
    // }

    private CustomerData convert(OrderPojo p, List<Customer> c) {
        CustomerData d = new CustomerData();
        d.setId(p.getId());
        d.setDateTime(p.getTime());
        d.setC(c);
        return d;
    }

    @ApiOperation(value = "Gets list of all Customer Orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
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

    // private static CustomerData convert2 (CustomerForm f)
    // {
    // CustomerData d = new CustomerData();

    // return d;

    // }

}
