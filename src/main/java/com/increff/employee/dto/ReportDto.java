package com.increff.employee.dto;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Data.InventoryReportData;
import com.increff.employee.model.Data.SalesReportData;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.DailySalesPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    private static DailySalesService dailySalesService;

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

//    public List<SalesReportData> getSalesReport(SalesReportForm salesReportForm) {
//    }

//    public List<DailySalesPojo> getDailySales() {
//        return dailySalesService.getAll();
//    }
}
