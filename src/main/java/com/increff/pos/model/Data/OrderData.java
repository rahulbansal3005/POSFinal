package com.increff.pos.model.Data;

import com.increff.pos.model.Form.OrderForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData extends OrderForm {
    private Integer id;
    private String dateTime;
    private Boolean status;
}
