package com.increff.employee.dto;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Data.InventoryReportData;
import com.increff.employee.model.Data.SalesReportData;
import com.increff.employee.model.Data.SalesReportUtil;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.SalesReportForm;
import com.increff.employee.pojo.*;
import com.increff.employee.service.*;
import com.increff.employee.util.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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


    public List<BrandForm> getBrandReport(BrandForm brandForm) {
        List<BrandPojo> brandPojoList = brandService.searchBrandCategoryData(brandForm);
        List<BrandForm> brandFormList = new ArrayList<BrandForm>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandFormList.add(helper.convertBrandPojotoBrandForm(brandPojo));
        }
        return brandFormList;
    }

    public List<InventoryReportData> getInventoryReport() throws ApiException {
        List<InventoryReportData> inventoryReportData = new ArrayList<>();
        List<BrandPojo> brandCategoryList = brandService.getAll();
        for (BrandPojo brandCategory : brandCategoryList) {
            InventoryReportData inventoryReportItemDataItem = new InventoryReportData();
            inventoryReportItemDataItem.setBrand(brandCategory.getBrand());
            inventoryReportItemDataItem.setCategory(brandCategory.getCategory());
            inventoryReportItemDataItem.setId(brandCategory.getId());
            int quantity = 0;
            List<ProductPojo> productList = productService.getProductByBrandCategoryId(brandCategory.getId());
            for (ProductPojo pojo : productList) {
                InventoryPojo inventoryPojo = inventoryService.get(pojo.getId());
                quantity += inventoryPojo.getQuantity();
            }
            inventoryReportItemDataItem.setQuantity(quantity);
            inventoryReportData.add(inventoryReportItemDataItem);
        }
        return inventoryReportData;
    }

    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) throws ApiException {
        List<SalesReportData> report = new ArrayList<>();

        //DEFINING START AND END DATE
        if (salesReportForm.getStartDate() == null) {
            Date begin = new Date();
            begin.setTime(1000);
            salesReportForm.setStartDate(begin);
        }
        if (salesReportForm.getEndDate() == null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(salesReportForm.getStartDate());
            c1.add(Calendar.DATE, 30);
            Date end = c1.getTime();
            salesReportForm.setEndDate(end);
        }

        if (salesReportForm.getStartDate().compareTo(salesReportForm.getEndDate()) > 0) {
            throw new ApiException("Start date should be before end date!!");
        }
        salesReportForm.setStartDate(getStartOfDay(salesReportForm.getStartDate(), Calendar.getInstance()));
        salesReportForm.setEndDate(getEndOfDay(salesReportForm.getEndDate(), Calendar.getInstance()));

        //
        String brand = salesReportForm.getBrand();
        String category = salesReportForm.getCategory();

        List<SalesReportUtil> allItems = new ArrayList<>();

        List<OrderPojo> orders = orderService.getAllInTimeDuration(salesReportForm.getStartDate(), salesReportForm.getEndDate());
        for (OrderPojo orderPojo : orders) {
            int id = orderPojo.getId();
            List<OrderItemPojo> items = orderItemService.getAllByOrderId(id);
            for (OrderItemPojo item : items) {
                int productId = item.getProductId();
                int quantity = item.getQuantity();
                double price = item.getSellingPrice();
                int brandId = productService.get(productId).getBrandCategory();


                SalesReportUtil curr = new SalesReportUtil();
                curr.setBrandId(brandId);
                curr.setQuantity(quantity);
                curr.setRevenue(quantity * price);

                allItems.add(curr);
            }
        }

        List<BrandPojo> allBrands = brandService.getByNameCategory(brand, category);

        Map<Integer, Integer> brandIdToQuantity = new HashMap<>();
        Map<Integer, Double> brandIdToRevenue = new HashMap<>();

        for (BrandPojo p : allBrands) {
            brandIdToQuantity.put(p.getId(), 0);
            brandIdToRevenue.put(p.getId(), Double.valueOf("0"));
        }

        for (SalesReportUtil item : allItems) {
            int brandId = item.getBrandId();
            int quantity = item.getQuantity();
            Double revenue = item.getRevenue();

            if (brandIdToQuantity.containsKey(brandId)) {
                int prevQuantity = brandIdToQuantity.get(brandId);
                double prevRevenue = brandIdToRevenue.get(brandId);

                brandIdToQuantity.put(brandId, prevQuantity + quantity);
                brandIdToRevenue.put(brandId, prevRevenue + revenue);
            }
        }

        for (BrandPojo p : allBrands) {
            SalesReportData data = new SalesReportData();
            data.setBrand(p.getBrand());
            data.setCategory(p.getCategory());
            int quantity = brandIdToQuantity.get(p.getId());
            double revenue = brandIdToRevenue.get(p.getId());
            data.setQuantity(quantity);
            data.setRevenue(revenue);

            report.add(data);
        }

        return report;
    }

    public static Date getStartOfDay(Date day,Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getEndOfDay(Date day,Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

//    public List<DailySalesPojo> getDailySales() {
//        return dailySalesService.getAll();
//    }
}
