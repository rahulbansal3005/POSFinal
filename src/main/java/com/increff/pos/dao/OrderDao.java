package com.increff.pos.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p ";
    private static String findBarCode = "select p from OrderPojo p where barcode=:barcode";
    private static String get_by_time = "select p from OrderPojo p where p.date>=:start and p.date<=:end";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }


    @Transactional
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
    @Transactional
    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {
    }

    public List<OrderPojo> selectAllOrdersInDateRange(LocalDateTime sdate, LocalDateTime edate) {
        TypedQuery<OrderPojo> query = getQuery(get_by_time, OrderPojo.class);
        query.setParameter("start", sdate);
        query.setParameter("end", edate);
        return query.getResultList();
    }

    // public OrderPojo getOrder_id(InventoryForm f) {
    // return null;
    // }

}
