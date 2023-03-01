package com.increff.pos.dto;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.Data.DailySalesData;
import com.increff.pos.model.Data.InventoryReportData;
import com.increff.pos.model.Data.SalesReportData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.SalesReportForm;
import com.increff.pos.pojo.*;
import com.increff.pos.service.*;
import com.increff.pos.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class ReportDto {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DailySalesService dailySalesService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ProductDao productDao;


    public List<BrandForm> getBrandReport(BrandForm brandForm) throws ApiException {
        List<BrandPojo> brandPojoList = brandService.searchBrandCategoryData(brandForm);
        List<BrandForm> brandFormList = new ArrayList<BrandForm>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandFormList.add(Helper.convertBrandPojotoBrandForm(brandPojo));
        }
        return brandFormList;
    }

    public List<InventoryReportData> getInventoryReport(BrandForm brandForm) throws ApiException {
        List<InventoryReportData> inventoryReportDataList = new ArrayList<>();
        List<BrandPojo> brandCategoryList = brandService.searchBrandCategoryData(brandForm);
        for (BrandPojo brandPojo : brandCategoryList) {
            List<ProductPojo> productPojoList = productService.getProductByBrandCategoryId(brandPojo.getId());
            if (productPojoList.size() != 0) {
                Integer quantity = 0;
                boolean flag=false;
                for (ProductPojo productPojo : productPojoList) {
                    InventoryPojo inventoryPojo = inventoryService.selectOnProdId(productPojo.getId());
                    if (inventoryPojo != null) {
                        quantity += inventoryPojo.getQuantity();
                       flag=true;
                    }
                }
                if(flag)
                {
                    InventoryReportData inventoryReportData = new InventoryReportData();
                    inventoryReportData.setQuantity(quantity);
                    inventoryReportData.setBrand(brandPojo.getBrand());
                    inventoryReportData.setCategory(brandPojo.getCategory());
                    inventoryReportData.setId(brandPojo.getId());
                    inventoryReportDataList.add(inventoryReportData);
                }
            }
        }
        return inventoryReportDataList;
    }

    public List<SalesReportData> getDateWiseSalesReport(SalesReportForm salesReportForm) throws ApiException {
//        System.out.println(salesReportForm.getStartDate() + "   "+ salesReportForm.getEndDate());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = salesReportForm.getStartDate();
        String endDate = salesReportForm.getEndDate();

        if (startDate == null || startDate.isEmpty()) {
            throw new ApiException("Start Date is empty");

        }

        if (endDate == null || endDate.isEmpty()) {
            throw new ApiException("End Date is empty");
        }
        LocalDate startLocalDate;
        LocalDate endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDate, dateFormatter);
            endLocalDate = LocalDate.parse(endDate, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ApiException("Invalid date format. Please use yyyy-MM-dd format");
        }

        if (!startLocalDate.isBefore(endLocalDate)) {
            throw new ApiException("Start Date should be before End Date");
        }



        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startDate = salesReportForm.getStartDate() + " 00:00:00";
        endDate = salesReportForm.getEndDate() + " 23:59:59";
        LocalDateTime sdate = LocalDateTime.parse(startDate, dateTimeFormatter);
        LocalDateTime edate = LocalDateTime.parse(endDate, dateTimeFormatter);
        int diff=edate.compareTo(sdate);
        if(diff>30)
        {
            throw new ApiException("Dates a");
        }
        List<OrderPojo> orderPojos = orderService.getOrdersInDateRange(sdate, edate);
        return reportService.get(orderPojos, salesReportForm.getBrand(), salesReportForm.getCategory());
    }

    public List<DailySalesData> getDailySales() {
        return dailySalesService.getAll();
    }

    public void createReport() {
        SalesPojo salesPojo=new SalesPojo();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDate localDate = now.toLocalDate();
        LocalDateTime start=now.with(LocalTime.MIN);
        LocalDateTime end=now.with(LocalTime.MAX);
        System.out.println(start);
        System.out.println(end);
        List<OrderPojo> orderPojos=orderService.getOrdersInDateRange(start,end);
        int invoiceCount=0;
        double totalRevenue=0;
        int totalInvoiceItems=0;
        for (OrderPojo orderPojo : orderPojos) {
            if(orderPojo.getIsInvoiceGenerated())
                invoiceCount++;
            List<OrderItemPojo> oip = orderItemService.getByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : oip) {

                totalInvoiceItems++;
                totalRevenue+=(orderItemPojo.getQuantity()*orderItemPojo.getSellingPrice());
            }
        }
        salesPojo.setInvoicedItemsCount(totalInvoiceItems);
        salesPojo.setTotalRevenue(totalRevenue);
        salesPojo.setLastRun(now);
        salesPojo.setInvoicedOrderCount(invoiceCount);
        salesPojo.setDate(localDate);
        reportService.addScheduler(salesPojo);
    }
}
