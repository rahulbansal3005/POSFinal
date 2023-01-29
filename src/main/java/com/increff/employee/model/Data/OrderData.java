package com.increff.employee.model.Data;

import com.increff.employee.model.Form.OrderForm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderData extends OrderForm {
    private int id;
    private Date dateTime;
}
