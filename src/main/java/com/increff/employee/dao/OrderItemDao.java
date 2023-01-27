package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

// import com.increff.employee.model.Customer;
// import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao {

    private static String delete_id = "delete from OrderItemPojo p where id=:id";
    private static String select_id = "select p from OrderItemPojo p where id=:id";
    private static String select_all = "select p from OrderItemPojo p ";
    private static String findBarCode = "select p from OrderItemPojo p where barcode=:barcode";
    private static String findAllOrderItems = "select p from OrderItemPojo p where orderId=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo orderItemPojo) {
        em.persist(orderItemPojo);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderItemPojo select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public OrderItemPojo select_barcode(String barCode) {
        TypedQuery<OrderItemPojo> query = getQuery(findBarCode, OrderItemPojo.class);
        query.setParameter("barcode", barCode);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
        return query.getResultList();
    }

    public List<OrderItemPojo> getAllOrderItemsbyOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(findAllOrderItems, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public void update(OrderItemPojo p) {
    }

    // public OrderItemPojo getOrderItem_id(InventoryForm f) {
    // return null;
    // }

}
