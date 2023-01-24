package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemPojo> orderItemPojoList) throws ApiException {
        for(OrderItemPojo orderItemPojo:orderItemPojoList)
        {
            orderItemDao.insert(orderItemPojo);
        }
    }


}
