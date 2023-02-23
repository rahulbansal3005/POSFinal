package com.increff.pos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.model.Form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;

import static com.increff.pos.util.Helper.convertOrderItemToOrderItemPojo;
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
        orderDao.insert(p);
    }


    @Transactional(rollbackOn = ApiException.class)
    public void addOrderItems(List<OrderItem> orderForm, int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList= new ArrayList<OrderItemPojo>();
        for(OrderItem orderItem:orderForm)
        {
//            TODO remove extractProdID
//            todo shift convert functions to dto

            OrderItemPojo orderItemPojo= convertOrderItemToOrderItemPojo(orderItem,orderId);
            orderItemPojo.setProductId(ps.extractProductId(orderItem.getBarCode()));
            orderItemPojoList.add(orderItemPojo);
        }
        add(orderItemPojoList);
    }


//    todo rollbackfor
    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemPojo> orderItemPojoList) throws ApiException {

        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            orderItemDao.insert(orderItemPojo);
        }
    }

     @Transactional(rollbackOn = ApiException.class)
    public List<OrderPojo> getAll(){
        return orderDao.selectAll();
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
        return getCheckonId(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo getOrderItem(int id) throws ApiException {
        return getCheckOrderItem(id);
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
        OrderPojo ex = getCheckonId(id);
        // ex.setBarcode(p.getBarcode());
        // ex.setBrand_category(p.getBrand_category());
        // ex.setName(p.getName());
        // ex.setMrp(p.getMrp());
        ex.setDate(p.getDate());
        orderDao.update(ex);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateOrderItem(int id, OrderItemPojo p) throws ApiException {
        // normalize(p);
        OrderItemPojo ex = getCheckOrderItem(id);
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
    public OrderItemPojo getCheckOrderItem(int id) throws ApiException {
        OrderItemPojo p = orderItemDao.select(id);
        if (p == null) {
            throw new ApiException("OrderItems with given ID does not exist, id: " + id);
        }
        return p;
    }

//    public void findingError(OrderForm form, HashMap<String, Integer> errors) {
//        for (OrderItem f : form.getOrderItemList()) {
//            String bc = f.getBarCode();
//            int quant = f.getQuantity();
//            ps.checker(bc, quant, errors);
//
//        }
//
//    }
    @Transactional(rollbackOn = ApiException.class)
    public void update(int id,LocalDateTime time) throws ApiException {
        OrderPojo ex = getCheckonId(id);
        ex.setIsInvoiceGenerated(true);
        ex.setInvoiceTime(time);
    }

    @Transactional
    public OrderPojo getCheckonId(int id) throws ApiException {
        OrderPojo orderPojo = orderDao.select(id);
        if (orderPojo == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return orderPojo;
    }

    public List<OrderPojo> getOrdersInDateRange(LocalDateTime sdate, LocalDateTime edate) {
        return orderDao.selectAllOrdersInDateRange(sdate,edate);
    }

//    @Transactional
//    public OrderPojo getCheckOrder(int id) throws ApiException {
//        OrderPojo p = orderDao.select(id);
//        if (p == null) {
//            throw new ApiException("Order with given ID does not exist, id: " + id);
//        }
//        return p;
//    }

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
