package com.increff.pos.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.model.Form.ProductForm;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;

import static com.increff.pos.util.Helper.createProductErrorobject;

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

//    @Transactional
//    public void delete(Integer id) {
//        productDao.delete(id);
//    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }


    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, String name, Double mrp) throws ApiException {
        ProductPojo newproductPojo = getCheck(id);
        newproductPojo.setName(name);
        newproductPojo.setMrp(mrp);
        productDao.update(newproductPojo);
    }

    @Transactional
    public ProductPojo getCheck(String barCode) {
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
    public int extractProductId(String barCode) throws ApiException {
        ProductPojo productPojo = productDao.select_barcode(barCode);
        if (productPojo == null) {
            throw new ApiException("Product does not exist in the Product List");
        }
        return productPojo.getId();
    }


    @Transactional
    public String extractBarCode(Integer prodID) throws ApiException {
        ProductPojo productPojo = productDao.select(prodID);
        if (productPojo == null) {
            throw new ApiException("Product does not exist with the given Product ID ");
        }
        return productPojo.getBarcode();
    }

//    @Transactional
//    public void checker(String barcode, Integer quant, HashMap<String, Integer> errors) {
//        ProductPojo p = productDao.select_barcode(barcode);
//        if (p == null) {
//            errors.put(barcode, -1);
//        } else {
//            Boolean v = inventoryService.checker(p.getId(), quant);
//            if (v) {
//                errors.put(barcode, quant);
//            }
//        }
//    }
    public void getCheckProductsInBulk(List<ProductForm> productForms, JSONArray array) {
        int index=1;
        for(ProductForm productForm:productForms)
        {
            ProductPojo productPojo=getCheck(productForm.getBarcode());
            if(productPojo!=null)
            {
                createProductErrorobject(productForm,array,"product already exist with row number: "+index);
            }
            index++;
        }
    }
}
