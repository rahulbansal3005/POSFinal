package com.increff.employee.service;

import com.increff.employee.dao.DailySalesDao;
import com.increff.employee.model.Data.DailySalesData;
import com.increff.employee.pojo.SalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service

public class DailySalesService {

    @Autowired
    private DailySalesDao dailySalesDao;


    @Transactional(rollbackOn = ApiException.class)
    public List<DailySalesData> getAll() {
        List<SalesPojo> salesPojoList=dailySalesDao.selectAll();
        List<DailySalesData> dailySalesDataList=new ArrayList<>();
        for(SalesPojo salesPojo:salesPojoList)
        {
            DailySalesData dailySalesData=new DailySalesData();
            String dateString = salesPojo.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dailySalesData.setDate(dateString);
            dailySalesData.setTotalRevenue(salesPojo.getTotalRevenue());
            dailySalesData.setInvoicedItemcount(salesPojo.getInvoicedItemsCount());
            dailySalesData.setInvoicedOrderCount(salesPojo.getInvoicedOrderCount());
            dailySalesDataList.add(dailySalesData);
        }


        return dailySalesDataList;
    }
//    @Scheduled(cron = "0 0 0 * * *")
//    @Transactional(rollbackOn = ApiException.class)
//    public void scheduler() throws ApiException {
//
//    }
}
