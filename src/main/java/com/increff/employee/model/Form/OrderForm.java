package com.increff.employee.model.Form;

import com.increff.employee.model.Data.OrderItem;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class OrderForm {
    private List<OrderItem> orderItemList;
}
