package com.increff.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.Form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.Data.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

import static com.increff.employee.util.helper.convertOrderItemToOrderItemPojo;
// import com.increff.employee.util.StringUtil;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductService ps;

    @Autowired

    private OrderItemDao orderItemDao;


    @Transactional(rollbackOn = ApiException.class)
    public void addOrder(OrderPojo p) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        orderDao.insert(p);
    }


    public void addOrderItems(OrderItem[] orderForm, int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList= new ArrayList<OrderItemPojo>();
        for(OrderItem orderItem:orderForm)
        {
//            TODO remove extractProdID
            OrderItemPojo orderItemPojo= convertOrderItemToOrderItemPojo(orderItem,orderId);
            orderItemPojo.setProductId(ps.extractProd_Id(orderItem.getBarCode()));
            orderItemPojoList.add(orderItemPojo);
        }
        add(orderItemPojoList);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemPojo> orderItemPojoList) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            System.out.println(orderItemPojo);
            orderItemDao.insert(orderItemPojo);
        }
    }

     @Transactional(rollbackOn = ApiException.class)
    public List<OrderPojo> getAll(){
        return OrderDao.selectAll();
    }

    @Transactional
    public void deleteOrder(int id) {
        orderDao.delete(id);
    }

    @Transactional
    public void deleteOrderItem(int id) {
        orderItemDao.delete(id);
    }

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
            temp.setSellingPrice(i.getSellingPrice());
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

    public List<OrderPojo> getAllInTimeDuration(Date startDate, Date endDate) {
        return orderDao.selectAllInTimeDuration(startDate, endDate);

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
