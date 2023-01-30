package com.increff.employee.service;

// import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
// import com.increff.employee.util.Validate;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo inventoryPojo) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        dao.insert(inventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, InventoryPojo inventoryPojo) throws ApiException {
        // normalize(p);
        InventoryPojo newInventoryPojo = getCheck(id);
        newInventoryPojo.setProductId(inventoryPojo.getProductId());
        newInventoryPojo.setQuantity(inventoryPojo.getQuantity());
        dao.update(newInventoryPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getCheck(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = dao.select(id);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return inventoryPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public Boolean checker(Integer id, Integer quant) {
        InventoryPojo inventoryPojo = dao.select(id);
        if (inventoryPojo.getQuantity() < quant) {
            return true;
        }
        return false;
    }


    @Transactional(rollbackOn = ApiException.class)
    public boolean checkQuantity(Integer id, Integer quant) {
        InventoryPojo inventoryPojo = dao.select(id);
        if (inventoryPojo.getQuantity()> quant) {
            return true;
        }
        return false;
    }
}
