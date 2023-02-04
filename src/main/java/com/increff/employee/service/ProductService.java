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
    private ProductDao productDao;

    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo productPojo) throws ApiException {
        productDao.insert(productPojo);
    }

    @Transactional
    public void delete(Integer id) {
        productDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }


    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, ProductPojo productPojo) throws ApiException {
        ProductPojo newproductPojo = getCheck(id);
        newproductPojo.setBarcode(productPojo.getBarcode());
        newproductPojo.setBrandCategory(productPojo.getBrandCategory());
        newproductPojo.setName(productPojo.getName());
        newproductPojo.setMrp(productPojo.getMrp());
        productDao.update(newproductPojo);
    }

    @Transactional
    public ProductPojo getCheck(String barCode) throws ApiException {
        ProductPojo productPojo = productDao.select_barcode(barCode);
        if (productPojo == null) {
            return null;
        }
        return productPojo;
    }

    @Transactional
    public ProductPojo getCheck(Integer id) throws ApiException {
        ProductPojo p = productDao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<ProductPojo> getProductByBrandCategoryId(int brandCategoryId) {
        return productDao.getProductByBrandCategory(brandCategoryId);
    }


    @Transactional
    public int extractProd_Id(String barCode) throws ApiException {
        ProductPojo p = productDao.select_barcode(barCode);
        if (p == null) {
            throw new ApiException("Product does not exist in the Product List");
        }
        return p.getId();
    }

    @Transactional
    public String extractBarCode(Integer prodID) throws ApiException {
        ProductPojo p = productDao.select(prodID);
        if (p == null) {
            throw new ApiException("Product does not does not exist with this Product ID ");
        }
        return p.getBarcode();
    }

    @Transactional
    public void checker(String barcode, Integer quant, HashMap<String, Integer> errors) {
        ProductPojo p = productDao.select_barcode(barcode);
        if (p == null) {
            errors.put(barcode, -1);
        } else {
            Boolean v = inventoryService.checker(p.getId(), quant);
            if (v) {
                errors.put(barcode, quant);
            }
        }
    }

}
