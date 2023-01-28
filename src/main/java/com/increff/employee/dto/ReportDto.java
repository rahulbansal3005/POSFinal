package com.increff.employee.dto;

import com.increff.employee.model.Data.InventoryReportData;
import com.increff.employee.model.Data.SalesReportData;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.SalesReportForm;
import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ReportDto {
    public List<BrandForm> getBrandReport(BrandForm form) {
        
    }

    public List<InventoryReportData> getInventoryReport() {
    }

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) {
    }

    public List<DailySalesPojo> getDailySales() {
    }
}
