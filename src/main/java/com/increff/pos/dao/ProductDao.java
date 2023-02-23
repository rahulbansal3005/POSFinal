package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p ";
    private static String findBarCode = "select p from ProductPojo p where barcode=:barcode";
    // private static String findBarCodeFromId = "select p from ProductPojo where";
    private static String select_Products_By_BrandId = "select p from ProductPojo p where brandCategory=:brandCategory";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

//    public int delete(Integer id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

    public ProductPojo select(Integer id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public ProductPojo select_barcode(String barCode) {
        TypedQuery<ProductPojo> query = getQuery(findBarCode, ProductPojo.class);
        query.setParameter("barcode", barCode);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }

    public List<ProductPojo> getProductByBrandCategory (int brandCategory) {
        TypedQuery<ProductPojo> query = getQuery(select_Products_By_BrandId, ProductPojo.class);
        query.setParameter("brandCategory",brandCategory);
        return query.getResultList();
    }
}
