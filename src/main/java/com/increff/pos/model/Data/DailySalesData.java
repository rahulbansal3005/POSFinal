package com.increff.pos.model.Data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailySalesData {
    private String date;
    private Integer invoicedOrderCount;
    private Integer invoicedItemcount;
    private Double totalRevenue;

}
