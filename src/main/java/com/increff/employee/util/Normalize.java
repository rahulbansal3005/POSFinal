package com.increff.employee.util;

import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.model.Form.ProductForm;
import com.increff.employee.model.Form.UserForm;

public class Normalize {


    public static void NormalizeBrandFormForbulkAdd(BrandForm brandForm){
        normalize(brandForm);
    }

    public static void normalize(BrandForm brandForm) {
        brandForm.setBrand(Validate.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(Validate.toLowerCase(brandForm.getCategory()));
    }

    public static void normalize(ProductForm productForm) {
        productForm.setName(Validate.toLowerCase(productForm.getName()));
        productForm.setCategory(Validate.toLowerCase(productForm.getCategory()));
        productForm.setBrand(Validate.toLowerCase(productForm.getBrand()));
        Double val=Math.round(productForm.getMrp()*100.0)/100.0;
        productForm.setMrp(val);
    }

     public static void normalize(InventoryForm inventoryForm) {
     inventoryForm.setBarcode(Validate.toLowerCase(inventoryForm.getBarcode()));
     }

    public static void normalize(UserForm userForm) {
        userForm.setEmail(userForm.getEmail().toLowerCase().trim());
//        userForm.setRole(userForm.getRole().toLowerCase().trim());
    }

    public static void NormalizeProductFormForbulkAdd(ProductForm productForm) {
        normalize(productForm);
    }
}
