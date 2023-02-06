package com.increff.employee.dao;


import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.pojo.SalesPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class DailySalesDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = ApiException.class)
    public void insert(SalesPojo salesPojo) {
        em.persist(salesPojo);
    }
}
