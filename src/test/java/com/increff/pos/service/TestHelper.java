package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;


public class TestHelper {
    @Autowired
    private BrandDto brandDto;
    @Autowired
    private static ProductDao productDao;
    @Autowired
    private static BrandDao brandDao;

    @Autowired
    private static BrandService brandService;

    public static BrandForm brandForm(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }

    public static ProductForm addProduct(String barcode, String brand, String category, String name, Double mrp)
    {
        ProductForm productForm = new ProductForm();
        productForm.setBarcode(barcode);
        productForm.setBrand(brand);
        productForm.setCategory(category);
        productForm.setName(name);
        productForm.setMrp(mrp);
        return productForm;
    }

    public static InventoryForm addInventory(String barcode, Integer quantity)
    {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode(barcode);
        inventoryForm.setQuantity(quantity);
        return inventoryForm;
    }
    public static InventoryPojo addInventoryPojo(Integer quantity, int pid)
    {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(pid);
        inventoryPojo.setQuantity(quantity);
        return inventoryPojo;
    }


    public static BrandPojo addBrandToPojo(String brand, String category) throws ApiException,NullPointerException {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand(brand);
        pojo.setCategory(category);
//        brandDao.insert(pojo);

        return pojo;
//        brandService.add(pojo);
//        return pojo.getId();
    }
    public static ProductPojo returnProductPojo(String barcode, String name, Double mrp, Integer bid) {
        ProductPojo productPojo=new ProductPojo();
        productPojo.setBarcode(barcode);
        productPojo.setName(name);
        productPojo.setMrp(mrp);
        productPojo.setBrandCategory(bid);
        return productPojo;
    }


    public static InventoryPojo returnInventoryPojo(Integer pid,Integer quantity)
    {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setQuantity(quantity);
        inventoryPojo.setProductId(pid);
        return inventoryPojo;
    }
}
