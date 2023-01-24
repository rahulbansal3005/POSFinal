package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

// import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.CustomerPojo;

@Repository
public class CustomerDao extends AbstractDao {

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from CustomerPojo p ";
    private static String findBarCode = "select p from CustomerPojo p where barcode=:barcode";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(CustomerPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public CustomerPojo select(int id) {
        TypedQuery<CustomerPojo> query = getQuery(select_id, CustomerPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public CustomerPojo select_barcode(String barCode) {
        TypedQuery<CustomerPojo> query = getQuery(findBarCode, CustomerPojo.class);
        query.setParameter("barcode", barCode);
        return getSingle(query);
    }

    public List<CustomerPojo> selectAll() {
        TypedQuery<CustomerPojo> query = getQuery(select_all, CustomerPojo.class);
        return query.getResultList();
    }

    public void update(CustomerPojo p) {
    }

    // public CustomerPojo getCustomer_id(InventoryForm f) {
    // return null;
    // }

}
