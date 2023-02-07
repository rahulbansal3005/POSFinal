package com.increff.employee.dao;
import com.increff.employee.pojo.SalesPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DailySalesDao extends AbstractDao {

    private static String select_all = "select p from SalesPojo p ";

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
}
