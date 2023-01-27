package com.increff.employee.model.Data;

import com.increff.employee.model.Form.ProductForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData extends ProductForm {
    private int id;
    private int brand_category;
}
