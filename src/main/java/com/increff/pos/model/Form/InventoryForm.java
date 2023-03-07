package com.increff.pos.model.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class InventoryForm {

//    @NotEmpty(message = "Name may not be empty")
    private String barcode;

    @Pattern(regexp="^(0|[1-9][0-9]*)$",message = "Must be an Integer")
    private Integer quantity;
}
