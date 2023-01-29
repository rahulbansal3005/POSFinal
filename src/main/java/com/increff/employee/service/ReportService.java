package com.increff.employee.service;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service

public class ReportService {
    public List<Integer> getOrderIdList(List<OrderPojo> orderPojo, String startdate, String enddate) throws ParseException, ApiException {
        List<Integer> orderIds = new ArrayList<Integer>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (OrderPojo orderPojo1 : orderPojo) {
            // Split datetime with space and get first element of array as date
            String receivedDate = sdf.format(orderPojo1.getDate());
            // Compares date with startdate and enddate
            if ((sdf.parse(startdate).before(sdf.parse(receivedDate))
                    || sdf.parse(startdate).equals(sdf.parse(receivedDate)))
                    && (sdf.parse(receivedDate).before(sdf.parse(enddate))
                    || sdf.parse(receivedDate).equals(sdf.parse(enddate)))) {
                // Add id to array
                orderIds.add(orderPojo1.getId());
            }
        }
        if (orderIds.size() == 0) {
            throw new ApiException("There are no orders for given dates");
        }
        return orderIds;
    }
}
