package com.increff.pos.service;

import com.increff.pos.dao.DailySalesDao;
import com.increff.pos.model.Data.DailySalesData;
import com.increff.pos.pojo.SalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service

public class DailySalesService {

    @Autowired
    private DailySalesDao dailySalesDao;


    @Transactional(rollbackOn = ApiException.class)
    public List<DailySalesData> getAll() {
        List<DailySalesData> dailySalesDataList=new ArrayList<>();
//        List<SalesPojo> salesPojoList=dailySalesDao.selectAll();
        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
        LocalDate localDate = now.toLocalDate();
//        System.out.println(localDate);
        List<SalesPojo> salesPojoListOfCurrentDate=dailySalesDao.getALL(localDate);
        for(SalesPojo salesPojo:salesPojoListOfCurrentDate)
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
