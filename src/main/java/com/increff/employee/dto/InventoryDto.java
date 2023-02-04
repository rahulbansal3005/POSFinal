package com.increff.employee.dto;


import com.increff.employee.model.Data.InventoryData;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.Helper.convertInventoryFormToInventoryPojo;
import static com.increff.employee.util.Helper.convertInventoryPojoToInventoryData;

@Service
public class InventoryDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;


    public void add( InventoryForm inventoryForm) throws ApiException {
        Validate.validateInventoryForm(inventoryForm);
        int prodId=productService.extractProd_Id(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo=inventoryService.selectOnProdId(prodId);
        if(inventoryPojo!=null)
        {
            inventoryService.updateIventory(inventoryPojo.getId(),inventoryForm.getQuantity());
            return;
        }
        else{
            inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);
            inventoryPojo.setProductId(prodId);
            inventoryService.add(inventoryPojo);
        }
    }
    public void delete(Integer id) {
        inventoryService.delete(id);
    }

    public InventoryData get( Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
//        return convertInventoryPojoToInventoryData(inventoryPojo);
        InventoryData inventoryData= convertInventoryPojoToInventoryData(inventoryPojo);
        inventoryData.setBarcode(productService.extractBarCode(inventoryPojo.getProductId()));
        return inventoryData;
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            InventoryData inventoryData= convertInventoryPojoToInventoryData(inventoryPojo);
            System.out.println(inventoryPojo.getProductId());
            inventoryData.setBarcode(productService.extractBarCode(inventoryPojo.getProductId()));
            inventoryDataList.add(inventoryData);
        }
        return inventoryDataList;
    }

    public void update( Integer id, InventoryForm inventoryForm) throws ApiException {
        Validate.validateInventoryForm(inventoryForm);
//        int productId=productService.extractProd_Id(inventoryForm.getBarcode());
//        InventoryPojo inventoryPojo1=inventoryService.selectOnProdId(productId);
//        if(inventoryPojo1!=null)
//        {
//            throw new ApiException("item already existed");
//        }
//        System.out.println('1');

//        InventoryPojo inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);
        inventoryService.update(id, inventoryForm.getQuantity());
//        System.out.println('2');
    }
}
