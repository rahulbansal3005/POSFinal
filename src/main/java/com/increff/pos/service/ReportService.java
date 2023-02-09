package com.increff.pos.service;

import com.increff.pos.dao.DailySalesDao;
import com.increff.pos.model.Data.SalesReportData;
import com.increff.pos.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            }
            else if (brand=="" && category!="") {
                if(category.equals(e.getValue().getCategory()))
                {
                    output.add(e.getValue());
                }
            }
            else if (brand!="" && category=="") {
                if(brand.equals(e.getValue().getBrand()))
                {
                    output.add(e.getValue());
                }
            }
            else{
                if(brand.equals(e.getValue().getBrand()) && category.equals(e.getValue().getCategory()))
                {
                    output.add(e.getValue());
                }
            }
        }
        return output;
    }

    public void addScheduler(SalesPojo salesPojo) {
        dailySalesDao.insert(salesPojo);
    }
//    public List<Integer> getOrderIdList(List<OrderPojo> orderPojo, String startdate, String enddate) throws ParseException, ApiException {
//        List<Integer> orderIds = new ArrayList<Integer>();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        for (OrderPojo orderPojo1 : orderPojo) {
//            // Split datetime with space and get first element of array as date
//            String receivedDate = sdf.format(orderPojo1.getDate());
//            // Compares date with startdate and enddate
//            if ((sdf.parse(startdate).before(sdf.parse(receivedDate))
//                    || sdf.parse(startdate).equals(sdf.parse(receivedDate)))
//                    && (sdf.parse(receivedDate).before(sdf.parse(enddate))
//                    || sdf.parse(receivedDate).equals(sdf.parse(enddate)))) {
//                // Add id to array
//                orderIds.add(orderPojo1.getId());
//            }
//        }
//        if (orderIds.size() == 0) {
//            throw new ApiException("There are no orders for given dates");
//        }
//        return orderIds;
//    }
}
