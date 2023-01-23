package com.increff.employee.util;

import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

public class Normalize {
    public static void normalize(BrandForm p) {
        p.setBrand(Validate.toLowerCase(p.getBrand()));
    }
}
