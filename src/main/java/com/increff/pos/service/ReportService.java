package com.increff.pos.service;

import com.increff.pos.dao.DailySalesDao;
import com.increff.pos.model.Data.SalesReportData;
import com.increff.pos.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class ReportService {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DailySalesDao dailySalesDao;


    public List<SalesReportData> get(List<OrderPojo> orderPojos, String brand, String category) throws ApiException {
        HashMap<Integer, SalesReportData> map = new HashMap<>();

        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> oip = orderItemService.getByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : oip) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.get(productPojo.getBrandCategory());
                if (!map.containsKey(brandPojo.getId())) {
                    map.put(brandPojo.getId(), new SalesReportData());
                }
                SalesReportData salesReportData = map.get(brandPojo.getId());
                salesReportData.setQuantity(salesReportData.getQuantity() + orderItemPojo.getQuantity());
                salesReportData.setRevenue(salesReportData.getRevenue() + (orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice()));
                salesReportData.setBrand(brandPojo.getBrand());
                salesReportData.setCategory(brandPojo.getCategory());
                map.replace(brandPojo.getId(), salesReportData);
            }
        }
        List<SalesReportData> output = new ArrayList<>();

        for (Map.Entry<Integer, SalesReportData> e : map.entrySet()) {
            if (brand == "" && category == "") {
                output.add(e.getValue());
            } else if (brand == "" && category != "") {
                if (category.equals(e.getValue().getCategory())) {
                    output.add(e.getValue());
                }
            } else if (brand != "" && category == "") {
                if (brand.equals(e.getValue().getBrand())) {
                    output.add(e.getValue());
                }
            } else {
                if (brand.equals(e.getValue().getBrand()) && category.equals(e.getValue().getCategory())) {
                    output.add(e.getValue());
                }
            }
        }
        return output;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void addScheduler(SalesPojo salesPojo) {
        dailySalesDao.insert(salesPojo);
    }

    public SalesPojo findSalesPojo(LocalDate date) {
        return dailySalesDao.get(date);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateSalesPojo(double totalRevenue, int totalInvoiceItems, LocalDateTime now, int invoiceCount) {
//        System.out.println(totalRevenue);
//        System.out.println(totalInvoiceItems);
//        System.out.println(invoiceCount);

        LocalDate localDate = now.toLocalDate();
        SalesPojo salesPojo1 = findSalesPojo(localDate);

        salesPojo1.setInvoicedItemsCount(totalInvoiceItems);
        salesPojo1.setTotalRevenue(totalRevenue);
        salesPojo1.setLastRun(now);
        salesPojo1.setInvoicedOrderCount(invoiceCount);

    }
}
