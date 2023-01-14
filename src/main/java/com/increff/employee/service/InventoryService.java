package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
// import com.increff.employee.util.StringUtil;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
        // normalize(p);
        // if (StringUtil.isEmpty(p.getName())) {
        // throw new ApiException("name cannot be empty");
        // }
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {
        // normalize(p);
        InventoryPojo ex = getCheck(id);
        ex.setProduct_id(p.getProduct_id());
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exist, id: " + id);
        }
        return p;
    }

    // protected static void normalize(InventoryPojo p) {
    // p.setName(StringUtil.toLowerCase(p.getName()));
    // }
}
