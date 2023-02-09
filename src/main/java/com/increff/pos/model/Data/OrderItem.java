package com.increff.pos.model.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private String barCode;
    private Integer quantity;
    private Double sellingPrice;
}
