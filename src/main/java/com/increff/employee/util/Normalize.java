package com.increff.employee.util;

import com.increff.employee.model.BrandForm;
import com.increff.employee.model.ProductForm;

public class Normalize {
    public static void normalize(BrandForm p) {
        p.setBrand(Validate.toLowerCase(p.getBrand()));
    }

    public static void normalize(ProductForm p) {
        p.setName(Validate.toLowerCase(p.getName()));
    }

}
