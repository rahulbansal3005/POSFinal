package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;


//    @Transactional(rollbackOn = ApiException.class)
//    public void add(List<OrderItemPojo> orderItemPojoList) throws ApiException {
//        for(OrderItemPojo orderItemPojo:orderItemPojoList)
//        {
//            orderItemDao.insert(orderItemPojo);
//        }
//    }


    public List<OrderItemPojo> getAllByOrderId(int orderId) {
        return orderItemDao.selectAllonOrderId(orderId);
    }

    public List<OrderItemPojo> getByOrderId(int id) {
        return orderItemDao.selectAllonOrderId(id);
    }
}
