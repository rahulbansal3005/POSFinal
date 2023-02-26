package com.increff.pos.service;

// import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.model.Data.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
// import com.increff.employee.util.Validate;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo inventoryPojo) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        inventoryDao.insert(inventoryPojo);
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void delete(Integer id) {
//        inventoryDao.delete(id);
//    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryPojo> getAll() {
        return inventoryDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, int quantity) throws ApiException {
        InventoryPojo newInventoryPojo = getCheck(id);
        newInventoryPojo.setQuantity(quantity);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getCheck(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.select(id);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return inventoryPojo;
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public Boolean checker(Integer id, Integer quant) {
//        InventoryPojo inventoryPojo = inventoryDao.select(id);
//        if (inventoryPojo.getQuantity() < quant) {
//            return true;
//        }
//        return false;
//    }


//    @Transactional(rollbackOn = ApiException.class)
//    public boolean checkQuantity(Integer id, Integer quant) {
//        InventoryPojo inventoryPojo = inventoryDao.select(id);
//        if (inventoryPojo.getQuantity()> quant) {
//            return true;
//        }
//        return false;
//    }



    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo selectOnProdId(int prodId)
    {
        InventoryPojo inventoryPojo=inventoryDao.findPojoOnProductId(prodId);
        if(inventoryPojo==null)
            return null;
        return inventoryPojo;
    }


    @Transactional(rollbackOn = ApiException.class)
    public void reduceInventory(OrderItem orderItem, int prod_id)
    {
        InventoryPojo inventoryPojo=inventoryDao.findPojoOnProductId(prod_id);
        int quantity = inventoryPojo.getQuantity();
        inventoryPojo.setQuantity(quantity-orderItem.getQuantity());
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateIventory(int id,int quantity) throws ApiException {
        InventoryPojo inventoryPojo=getCheck(id);
        int quant=inventoryPojo.getQuantity();
        inventoryPojo.setQuantity(quantity+quant);
    }
}
