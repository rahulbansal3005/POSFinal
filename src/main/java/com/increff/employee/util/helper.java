package com.increff.employee.util;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

public class helper {
    public static BrandData convertBrandPojoToBrandData(BrandPojo p) {
        BrandData brandData = new BrandData();
        brandData.setCategory(p.getCategory());
        brandData.setBrand(p.getBrand());
        brandData.setId(p.getId());
        return brandData;
    }

    public static BrandPojo convertBrandFormToBrandPojo(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
}
