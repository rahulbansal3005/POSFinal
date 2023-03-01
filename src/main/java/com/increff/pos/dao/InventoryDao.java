package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

    private static final String DELETE_ID = "delete from InventoryPojo p where id=:id";
    private static final String SELECT_ID = "select p from InventoryPojo p where id=:id";
    private static final String SELECT_ALL = "select p from InventoryPojo p";

    private static final String SELECT_ON_PROD_ID = "select p from InventoryPojo p where productId=:productId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em.persist(inventoryPojo);
    }

//    public int delete(Integer id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }

    public InventoryPojo select(Integer id) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ID, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }

//    public void update(InventoryPojo inventoryPojo) {
//    }


//    public List<InventoryPojo> selectOnProdId(int productId)
//    {
//        TypedQuery<InventoryPojo> query = getQuery(select_on_prod_id, InventoryPojo.class);
//        query.setParameter("productId", productId);
//        return getSingle(query);
//    }

    public InventoryPojo findPojoOnProductId(int productId) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ON_PROD_ID, InventoryPojo.class);
        query.setParameter("productId", productId);
        return getSingle(query);
    }
}
