package com.increff.employee.util;

import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.model.Form.ProductForm;

public class Normalize {
    public static void normalize(BrandForm brandForm) {
        brandForm.setBrand(Validate.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(Validate.toLowerCase(brandForm.getCategory()));
    }

    public static void normalize(ProductForm productForm) {
        productForm.setName(Validate.toLowerCase(productForm.getName()));
        productForm.setCategory(Validate.toLowerCase(productForm.getCategory()));
        productForm.setBrand(Validate.toLowerCase(productForm.getBrand()));
    }

     public static void normalize(InventoryForm inventoryForm) {
     inventoryForm.setBarcode(Validate.toLowerCase(inventoryForm.getBarcode()));
     }
}
