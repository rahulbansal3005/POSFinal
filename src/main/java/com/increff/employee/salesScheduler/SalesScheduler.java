package com.increff.employee.salesScheduler;

import com.increff.employee.dto.ReportDto;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class SalesScheduler {
    @Autowired
    ReportDto reportDto;

    @Async
    @Scheduled(cron = "0 0 12 * * *")
    public void createReport() throws ApiException {
        reportDto.createReport();
    }
}
