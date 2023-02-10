package com.increff.pos.util;

import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.model.Form.UserForm;

public class Normalize {


    public static void NormalizeBrandFormForbulkAdd(BrandForm brandForm){
        normalizeBrandForm(brandForm);
    }

    public static void normalizeBrandForm(BrandForm brandForm) {
        brandForm.setBrand(Validate.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(Validate.toLowerCase(brandForm.getCategory()));
    }

    public static void normalizeProductForm(ProductForm productForm) {
        productForm.setName(Validate.toLowerCase(productForm.getName()));
        productForm.setCategory(Validate.toLowerCase(productForm.getCategory()));
        productForm.setBrand(Validate.toLowerCase(productForm.getBrand()));
        Double val=Math.round(productForm.getMrp()*100.0)/100.0;
        productForm.setMrp(val);
    }

     public static void normalizeInventoryForm(InventoryForm inventoryForm) {
     inventoryForm.setBarcode(Validate.toLowerCase(inventoryForm.getBarcode()));
     }

    public static void normalizeUserForm(UserForm userForm) {
        userForm.setEmail(userForm.getEmail().toLowerCase().trim());
//        userForm.setRole(userForm.getRole().toLowerCase().trim());
    }

    public static void NormalizeProductFormForbulkAdd(ProductForm productForm) {
        normalizeProductForm(productForm);
    }
}
