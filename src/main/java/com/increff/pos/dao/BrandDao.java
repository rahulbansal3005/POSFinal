package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

    private static final String DELETE_ID = "delete from BrandPojo p where id=:id";
    private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
    private static final String SELECT_ALL = "select p from BrandPojo p";

    private static final String SELECT_CATEGORY = "select b from BrandPojo b where brand=:brand";
    private static final String BRAND_CAT_ID = "select p from BrandPojo p where brand=:brand and category=:category";
    private static final String SELECT_BRAND = "select p from BrandPojo p where category=:category";
    private static final String SEARCH = "select p from BrandPojo p where brand like :brand and category like :category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

//    public int delete(int id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

//    public void update(BrandPojo brandPojo) {
//    }

    public BrandPojo getBrand_category(ProductForm productForm) {
        TypedQuery<BrandPojo> query = getQuery(BRAND_CAT_ID, BrandPojo.class);
        query.setParameter("brand", productForm.getBrand());
        query.setParameter("category", productForm.getCategory());
        return getSingle(query);
    }

    public List<BrandPojo> getCategory(String brand){
        TypedQuery<BrandPojo> query = getQuery(SELECT_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public List<BrandPojo> searchBrandData(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(SEARCH, BrandPojo.class);
        query.setParameter("brand", brand+"%");
        query.setParameter("category", category+"%");
        return query.getResultList();
    }

    public List<BrandPojo> selectByCategory(String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND, BrandPojo.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<BrandPojo> selectByBrand(String brand) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public BrandPojo selectonBrandCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(BRAND_CAT_ID, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }
}
