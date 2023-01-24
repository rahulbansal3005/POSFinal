package com.increff.employee.util;

import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class helper {

//    @Autowired
    private static BrandService bs;
    private static ProductService ps;

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

    public static ProductData convertProductPojoToProductData(ProductPojo productPojo) {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand_category(productPojo.getBrand_category());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        return productData;
    }

    public static ProductPojo convertProductFormToProductPojo(ProductForm productForm) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setBrand_category(bs.extractId(productForm));
        productPojo.setName(productForm.getName());
        productPojo.setMrp(productForm.getMrp());
        return productPojo;
    }


    public static InventoryData convertInventoryPojoToInventoryData(InventoryPojo inventoryPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setid(inventoryPojo.getId());
        inventoryData.setProduct_id(inventoryPojo.getProduct_id());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        return inventoryData;
    }




    public static InventoryPojo convertInventoryFormToInventoryPojo(InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProduct_id(ps.extractProd_Id(inventoryForm.getBarcode()));
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }
}
