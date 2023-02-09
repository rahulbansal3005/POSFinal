package com.increff.pos.model.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportData {
    public Integer id;
    private String brand;
    private String category;
    private Integer quantity;
}
