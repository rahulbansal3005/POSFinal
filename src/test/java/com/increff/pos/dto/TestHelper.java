package com.increff.pos.dto;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;

public class TestHelper {
    @Autowired
    private BrandDto brandDto;

    @Autowired
    private static BrandDao brandDao;


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


    public static Integer addBrandToPojo(String brand, String category) {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand(brand);
        pojo.setCategory(category);
        brandDao.insert(pojo);
        return pojo.getId();
    }
}
