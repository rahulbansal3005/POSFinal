package com.increff.employee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.increff.employee.dao.CustomerDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderItem;
// import com.increff.employee.model.InventoryForm;
// import com.increff.employee.pojo.CustomerPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
// import com.increff.employee.util.StringUtil;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    // @Autowired
    // private InventoryService is;

    @Autowired
    private ProductService ps;

    @Autowired

    private OrderItemDao orderItemDao;

    // @Autowired
    // private BrandService bs;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo p) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        orderDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemPojo> p) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        for (OrderItemPojo i : p) {
            orderItemDao.insert(i);
        }
    }

    // @Transactional(rollbackOn = ApiException.class)
    // List<OrderPojo> getAll()

    @Transactional
    public void deleteOrder(int id) {
        orderDao.delete(id);
    }

    @Transactional
    public void deleteOrderItem(int id) {
        orderItemDao.delete(id);
    }

    // @Transactional(rollbackOn = ApiException.class)
    // public CustomerPojo get(int id) throws ApiException {
    // return getCheck(id);
    // }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo getOrder(int id) throws ApiException {
        return getCheckOrder(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo getOrderItem(int id) throws ApiException {
        return getCheckOrerItem(id);
    }

    @Transactional
    public List<OrderPojo> getAllOrder() {
        return orderDao.selectAll();
    }

    @Transactional
    public List<OrderItemPojo> getAllOrderItems() {
        return orderItemDao.selectAll();
    }

    @Transactional
    public List<OrderItem> getOrderItems(int orderId) throws ApiException {
        List<OrderItem> c = new ArrayList<OrderItem>();
        List<OrderItemPojo> p = orderItemDao.getAllOrderItemsbyOrderId(orderId);
        for (OrderItemPojo i : p) {
            OrderItem temp = new OrderItem();
            temp.setBarCode(ps.extractBarCode(i.getProductId()));
            temp.setMrp(i.getSellingPrice());
            temp.setQuantity(i.getQuantity());

            c.add(temp);
        }
        return c;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateOrder(int id, OrderPojo p) throws ApiException {
        // normalize(p);
        OrderPojo ex = getCheckOrder(id);
        // ex.setBarcode(p.getBarcode());
        // ex.setBrand_category(p.getBrand_category());
        // ex.setName(p.getName());
        // ex.setMrp(p.getMrp());
        ex.setTime(p.getTime());
        orderDao.update(ex);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateOrderItem(int id, OrderItemPojo p) throws ApiException {
        // normalize(p);
        OrderItemPojo ex = getCheckOrerItem(id);
        // ex.setBarcode(p.getBarcode());
        // ex.setBrand_category(p.getBrand_category());
        // ex.setName(p.getName());
        // ex.setMrp(p.getMrp());

        orderItemDao.update(ex);
    }

    @Transactional
    public OrderItemPojo getCheck(String barCode) throws ApiException {
        OrderItemPojo p = orderItemDao.select_barcode(barCode);
        if (p == null) {
            throw new ApiException("Customer with given ID does not exit, id: " + barCode);
        }
        return p;
    }

    @Transactional
    public OrderPojo getCheckOrder(int id) throws ApiException {
        OrderPojo p = orderDao.select(id);
        if (p == null) {
            throw new ApiException("Order with given ID does not exist, id: " + id);
        }
        return p;
    }

    @Transactional
    public OrderItemPojo getCheckOrerItem(int id) throws ApiException {
        OrderItemPojo p = orderItemDao.select(id);
        if (p == null) {
            throw new ApiException("OrderItems with given ID does not exist, id: " + id);
        }
        return p;
    }

    public void findingError(OrderForm form, HashMap<String, Integer> errors) {
        for (OrderItem f : form.getC()) {
            String bc = f.getBarCode();
            int quant = f.getQuantity();
            ps.checker(bc, quant, errors);

        }

    }

    // protected static void normalize(CustomerPojo p) {
    // p.setName(StringUtil.toLowerCase(p.getName()));
    // }

    // @Transactional
    // public int extractProd_Id(InventoryForm f) throws ApiException {
    // CustomerPojo p = dao.select_barcode(f.getBarcode());
    // if (p == null) {
    // throw new ApiException("Customer does not does not exist ");
    // }
    // return p.getId();
    // }

    // public
}
