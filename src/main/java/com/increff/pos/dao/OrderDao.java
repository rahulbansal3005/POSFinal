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

    private static final String DELETE_ID = "delete from OrderPojo p where id=:id";
    private static final String SELECT_ID = "select p from OrderPojo p where id=:id";
    private static final String SELECT_ALL = "select p from OrderPojo p ";
    private static final String FINDBARCODE = "select p from OrderPojo p where barcode=:barcode";
    private static final String GET_BY_TIME = "select p from OrderPojo p where p.date>=:start and p.date<=:end";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }


//    @Transactional
//    public int delete(int id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ID, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

//    public OrderPojo select_barcode(String barCode) {
//        TypedQuery<OrderPojo> query = getQuery(findBarCode, OrderPojo.class);
//        query.setParameter("barcode", barCode);
//        return getSingle(query);
//    }
    @Transactional
    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

//    public void update(OrderPojo p) {
//    }

    public List<OrderPojo> selectAllOrdersInDateRange(LocalDateTime sdate, LocalDateTime edate) {
        TypedQuery<OrderPojo> query = getQuery(GET_BY_TIME, OrderPojo.class);
        query.setParameter("start", sdate);
        query.setParameter("end", edate);
        return query.getResultList();
    }

    // public OrderPojo getOrder_id(InventoryForm f) {
    // return null;
    // }

}
