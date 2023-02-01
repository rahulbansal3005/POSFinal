package com.increff.employee.model.Form;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class SalesReportForm {
    //    private String brand;
//    private String category;
//    private Date startDate;
//    private Date endDate;
    public String startDate;
    public String endDate;
    public String brand;
    public String category;
}
