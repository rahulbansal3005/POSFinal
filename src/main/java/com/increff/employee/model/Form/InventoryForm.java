package com.increff.employee.model.Form;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class InventoryForm {

//    @NotEmpty(message = "Name may not be empty")
    private String barcode;
    private Integer quantity;
}
