package com.increff.employee.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private InventoryService is;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
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
    public void update(int id, ProductPojo productPojo) throws ApiException {
        ProductPojo newproductPojo = getCheck(id);
        newproductPojo.setBarcode(productPojo.getBarcode());
        newproductPojo.setBrand_category(productPojo.getBrand_category());
        newproductPojo.setName(productPojo.getName());
        newproductPojo.setMrp(productPojo.getMrp());
        dao.update(newproductPojo);
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


    @Transactional
    public int extractProd_Id(String barCode) throws ApiException {
        ProductPojo p = dao.select_barcode(barCode);
        if (p == null) {
            throw new ApiException("Product does not does not exist ");
        }
        return p.getId();
    }

    @Transactional
    public String extractBarCode(int prodID) throws ApiException {
        ProductPojo p = dao.select(prodID);
        if (p == null) {
            throw new ApiException("Product does not does not exist with this Product ID ");
        }
        return p.getBarcode();
    }

    @Transactional
    public void checker(String barcode, int quant, HashMap<String, Integer> errors) {
        ProductPojo p = dao.select_barcode(barcode);
        if (p == null) {
            errors.put(barcode, -1);
        } else {
            Boolean v = is.checker(p.getId(), quant);
            if (v) {
                errors.put(barcode, quant);
            }
        }
    }

}
