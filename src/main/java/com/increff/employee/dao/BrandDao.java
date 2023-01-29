package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.model.Form.ProductForm;
import com.increff.employee.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

    private static String delete_id = "delete from BrandPojo p where id=:id";
    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";

    private static String select_category = "select b from BrandPojo b where brand=:brand";
    private static String brand_cat_id = "select p from BrandPojo p where brand=:brand and category=:category";
    private static String select_cat = "select p from BrandPojo p where category=:category";
    private static String search = "select p from BrandPojo p where brand like :brand and category like :category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo brandPojo) {
    }

    public BrandPojo getBrand_category(ProductForm productForm) {
        TypedQuery<BrandPojo> query = getQuery(brand_cat_id, BrandPojo.class);
        query.setParameter("brand", productForm.getBrand());
        query.setParameter("category", productForm.getCategory());
        return getSingle(query);
    }

    public List<BrandPojo> getCategory(String brand){
        TypedQuery<BrandPojo> query = getQuery(select_category, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public List<BrandPojo> searchBrandData(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(search, BrandPojo.class);
        query.setParameter("brand", brand+"%");
        query.setParameter("category", category+"%");
        return query.getResultList();
    }

    public List<BrandPojo> selectByCategory(String category) {
        TypedQuery<BrandPojo> query = getQuery(select_cat, BrandPojo.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<BrandPojo> selectByName(String brand) {
        TypedQuery<BrandPojo> query = getQuery(select_category, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public BrandPojo select(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(brand_cat_id, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return query.getSingleResult();
    }
}
