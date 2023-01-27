package com.increff.employee.util;

import com.increff.employee.model.*;
import com.increff.employee.pojo.*;
import com.increff.employee.service.ApiException;

import java.util.List;

public class helper {

    public static BrandData convertBrandPojoToBrandData(BrandPojo p) {
        BrandData brandData = new BrandData();
        brandData.setCategory(p.getCategory());
        brandData.setBrand(p.getBrand());
        brandData.setId(p.getId());
        return brandData;
    }

    public static BrandPojo convertBrandFormToBrandPojo(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }

    public static ProductData convertProductPojoToProductData(ProductPojo productPojo) {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand_category(productPojo.getBrandCategory());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        return productData;
    }

    public static ProductPojo convertProductFormToProductPojo(ProductForm productForm) throws ApiException {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setName(productForm.getName());
        productPojo.setMrp(productForm.getMrp());
        return productPojo;
    }


    public static InventoryData convertInventoryPojoToInventoryData(InventoryPojo inventoryPojo) {
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setProduct_id(inventoryPojo.getProductId());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        return inventoryData;
    }




    public static InventoryPojo convertInventoryFormToInventoryPojo(InventoryForm inventoryForm) throws ApiException {
//        TODO helper cannot use services like productServices.
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }

    public static OrderItemPojo convertOrderItemToOrderItemPojo(OrderItem orderItem,int orderId) throws ApiException {
        OrderItemPojo orderItemPojo =new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setQuantity(orderItem.getQuantity());
        orderItemPojo.setSellingPrice(orderItem.getSellingPrice());
        return orderItemPojo;
    }
//    public static List<OrderItemPojo> convertOrderFormToOrderItemPojoList(OrderForm orderForm, int orderId) throws ApiException {
//        List<OrderItemPojo> orderItemPojoList = new ArrayList<OrderItemPojo>();
//        for (OrderItem orderItemForm : orderForm.getC()) {
//            OrderItemPojo orderItemPojo = new OrderItemPojo();
//            orderItemPojo.setOrderId(orderId);
//            orderItemPojo.setProductId(ps.extractProd_Id(orderItemForm.getBarCode()));
//            orderItemPojo.setQuantity(orderItemForm.getQuantity());
//            orderItemPojo.setSellingPrice(orderItemForm.getMrp());
//            orderItemPojoList.add(orderItemPojo);
//        }
//        return orderItemPojoList;
//    }

    public static OrderPojo convertOrderFormToOrder() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        return orderPojo;
    }

    public static OrderData convertOrderPojoToOrderData(OrderPojo p, List<OrderItem> c) {
        OrderData d = new OrderData();
        d.setId(p.getId());
        d.setDateTime(p.getTime());
        d.setC(c);
        return d;
    }
}
