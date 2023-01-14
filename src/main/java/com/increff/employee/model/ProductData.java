package com.increff.employee.model;

public class ProductData extends ProductForm {
    private int id;
    private int brand_category;

    public int getId() {
        return id;
    }

    public int getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(int brand_category) {
        this.brand_category = brand_category;
    }

    public void setId(int id) {
        this.id = id;
    }
}
