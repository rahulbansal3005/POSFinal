package com.increff.employee.service;

import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service

public class DailySalesService {

    @Transactional(rollbackOn = ApiException.class)
    public List<DailySalesPojo> getAll() {
        List<DailySalesPojo> dailySalesPojoList=new ArrayList<>();
        return dailySalesPojoList;
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional(rollbackOn = ApiException.class)
    public void scheduler() throws ApiException {

    }
}
