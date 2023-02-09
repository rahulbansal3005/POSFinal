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

    private static String delete_id = "delete from InventoryPojo p where id=:id";
    private static String select_id = "select p from InventoryPojo p where id=:id";
    private static String select_all = "select p from InventoryPojo p";

    private static String select_on_prod_id = "select p from InventoryPojo p where productId=:productId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo inventoryPojo) {
        em.persist(inventoryPojo);
    }

    public int delete(Integer id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public InventoryPojo select(Integer id) {
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo inventoryPojo) {
    }


//    public List<InventoryPojo> selectOnProdId(int productId)
//    {
//        TypedQuery<InventoryPojo> query = getQuery(select_on_prod_id, InventoryPojo.class);
//        query.setParameter("productId", productId);
//        return getSingle(query);
//    }

    public InventoryPojo findPojoOnProductId(int productId) {
        TypedQuery<InventoryPojo> query = getQuery(select_on_prod_id, InventoryPojo.class);
        query.setParameter("productId", productId);
        return getSingle(query);
    }
}
