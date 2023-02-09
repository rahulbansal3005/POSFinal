package com.increff.pos.spring;

import com.increff.pos.salesScheduler.SalesScheduler;
//import com.increff.employee.scheduler.SalesScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    public SalesScheduler schedule()
    {
        return new SalesScheduler();
    }

}
