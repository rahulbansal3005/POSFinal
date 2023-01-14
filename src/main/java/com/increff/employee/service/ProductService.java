package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    // @Autowired
    // private BrandService bs;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        normalize(p);
        if (StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setBarcode(p.getBarcode());
        ex.setBrand_category(p.getBrand_category());
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        dao.update(ex);
    }

    @Transactional
    public ProductPojo getCheck(String barCode) throws ApiException {
        ProductPojo p = dao.select_barcode(barCode);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + barCode);
        }
        return p;
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    @Transactional
    public int extractProd_Id(InventoryForm f) throws ApiException {
        ProductPojo p = dao.select_barcode(f.getBarcode());
        if (p == null) {
            throw new ApiException("Product does not does not exist ");
        }
        return p.getId();
    }

    // public
}
