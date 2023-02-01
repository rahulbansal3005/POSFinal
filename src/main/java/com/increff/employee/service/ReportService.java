package com.increff.employee.service;

import com.increff.employee.model.Data.SalesReportData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service

public class ReportService {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;
    public List<SalesReportData> get(List<OrderPojo> orderPojos, String brand, String category) throws ApiException {
        HashMap<Integer,SalesReportData> map = new HashMap<>();

        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> oip = orderItemService.getByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : oip) {
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.get(productPojo.getBrandCategory());
                if (!map.containsKey(brandPojo.getId())) {
                    map.put(brandPojo.getId(), new SalesReportData());
                }
                SalesReportData d = map.get(brandPojo.getId());
                d.setQuantity(d.getQuantity() + orderItemPojo.getQuantity());
                d.setRevenue(d.getRevenue() + (orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice()));
            }
        }


        List<SalesReportData> output = new ArrayList<>();

        for (Map.Entry<Integer,SalesReportData> e : map.entrySet()){

            BrandPojo bp = brandService.get(e.getKey());
            if((Objects.equals(brand,bp.getBrand()) || Objects.equals(brand,"all")) && (Objects.equals(category,bp.getCategory()) || Objects.equals(category,"all"))){
                SalesReportData salesReportData = e.getValue();
                salesReportData.setBrand(bp.getBrand());
                salesReportData.setCategory(bp.getCategory());
                output.add(salesReportData);
            }
        }


        return output;
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
