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

    private static final String SELECT_ALL = "select p from SalesPojo p ";
    private static final String GET_TODAYS = "select p from SalesPojo p where p.date=:today";
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(SalesPojo salesPojo) {
        em.persist(salesPojo);
    }
    public List<SalesPojo> selectAll() {
        TypedQuery<SalesPojo> query = getQuery(SELECT_ALL, SalesPojo.class);
        return query.getResultList();
    }

    public SalesPojo get(LocalDate today){
        TypedQuery<SalesPojo> query = getQuery(GET_TODAYS, SalesPojo.class);
        query.setParameter("today", today);
        return getSingle(query);
    }


    public List<SalesPojo> getALL(LocalDate today){
        TypedQuery<SalesPojo> query = getQuery(GET_TODAYS, SalesPojo.class);
        query.setParameter("today", today);
        return query.getResultList();
    }
}

