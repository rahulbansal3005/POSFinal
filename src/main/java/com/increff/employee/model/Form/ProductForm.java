package com.increff.employee.model.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    private String barcode;
    private String brand;
    private String name;
    private double mrp;
    private String category;
}