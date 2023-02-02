package com.increff.employee.dto;


import com.increff.employee.model.Data.InventoryData;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
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
        InventoryPojo inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);

        inventoryPojo.setProductId(productService.extractProd_Id(inventoryForm.getBarcode()));

        inventoryService.add(inventoryPojo);
    }
    public void delete(Integer id) {
        inventoryService.delete(id);
    }

    public InventoryData get( Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        return convertInventoryPojoToInventoryData(inventoryPojo);
    }


    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            InventoryData inventoryData= convertInventoryPojoToInventoryData(inventoryPojo);
            inventoryData.setBarcode(productService.extractBarCode(inventoryPojo.getProductId()));
            inventoryDataList.add(inventoryData);
        }
        return inventoryDataList;
    }

    public void update( Integer id, InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);
        inventoryService.update(id, inventoryPojo);
    }
}
