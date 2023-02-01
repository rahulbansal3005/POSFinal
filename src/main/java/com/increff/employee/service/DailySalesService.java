package com.increff.employee.service;

import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service

public class DailySalesService {
    public List<DailySalesPojo> getAll() {
        List<DailySalesPojo> dailySalesPojoList=new ArrayList<>();
        return dailySalesPojoList;
    }
}
