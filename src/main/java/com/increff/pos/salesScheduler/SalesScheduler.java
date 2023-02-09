package com.increff.pos.salesScheduler;

import com.increff.pos.dto.ReportDto;
import com.increff.pos.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;

@EnableAsync
public class SalesScheduler {
    @Autowired
    ReportDto reportDto;

    @Async
    @Scheduled(cron = "0 0 12 * * *")
    public void createReport() throws ApiException, ParseException {
        reportDto.createReport();
    }
}
