package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p ";
    private static String findBarCode = "select p from OrderPojo p where barcode=:barcode";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public OrderPojo select_barcode(String barCode) {
        TypedQuery<OrderPojo> query = getQuery(findBarCode, OrderPojo.class);
        query.setParameter("barcode", barCode);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {
    }

    // public OrderPojo getOrder_id(InventoryForm f) {
    // return null;
    // }

}
