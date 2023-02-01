package com.increff.employee.model.Form;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class SalesReportForm {
    public String getStartdate() {
        return startDate;
    }

    public void setStartdate(String startDate) {
        this.startDate = startDate;
    }

    public String getEnddate() {
        return endDate;
    }

    public void setEnddate(String endDate) {
        this.endDate = endDate;
    }

    //    private String brand;
//    private String category;
//    private Date startDate;
//    private Date endDate;
    public String startDate;
    public String endDate;
    public String brand;
    public String category;
}
