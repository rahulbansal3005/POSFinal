package com.increff.pos.dao;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public class DailySalesDao extends AbstractDao {

    private static String select_all = "select p from SalesPojo p ";
    private static String get_todays = "select p from SalesPojo p where p.date=:today";
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(SalesPojo salesPojo) {
        em.persist(salesPojo);
    }
    public List<SalesPojo> selectAll() {
        TypedQuery<SalesPojo> query = getQuery(select_all, SalesPojo.class);
        return query.getResultList();
    }

    public List<SalesPojo> get(LocalDate today){
        TypedQuery<SalesPojo> query = getQuery(get_todays, SalesPojo.class);
        query.setParameter("today", today);
        return query.getResultList();
    }
}
