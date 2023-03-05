package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

// import com.increff.employee.model.Customer;
// import com.increff.employee.model.Form.InventoryForm;
import com.increff.pos.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao {

    private static final String DELETE_ID = "delete from OrderItemPojo p where id=:id";
    private static final String SELECT_ID = "select p from OrderItemPojo p where id=:id";

    private static final String SELECT_ALL = "select p from OrderItemPojo p ";
    private static final String FINDBARCODE = "select p from OrderItemPojo p where barcode=:barcode";
    private static final String FINDALLORDERITEMS = "select p from OrderItemPojo p where orderId=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo) {
        em.persist(orderItemPojo);
    }

//    public int delete(int id) {
//        Query query = em.createQuery(DELETE_ID);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

//    public OrderItemPojo select(int id) {
//        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID, OrderItemPojo.class);
//        query.setParameter("id", id);
//        return getSingle(query);
//    }

//    public OrderItemPojo select_barcode(String barCode) {
//        TypedQuery<OrderItemPojo> query = getQuery(FINDBARCODE, OrderItemPojo.class);
//        query.setParameter("barcode", barCode);
//        return getSingle(query);
//    }

//    public List<OrderItemPojo> selectAll() {
//        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
//        return query.getResultList();
//    }

    public List<OrderItemPojo> getAllOrderItemsbyOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(FINDALLORDERITEMS, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

//    public void update(OrderItemPojo p) {
//    }

    public List<OrderItemPojo> selectAllonOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(FINDALLORDERITEMS, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    // public OrderItemPojo getOrderItem_id(InventoryForm f) {
    // return null;
    // }

}
