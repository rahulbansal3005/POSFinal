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

import static com.increff.employee.util.helper.convertInventoryFormToInventoryPojo;
import static com.increff.employee.util.helper.convertInventoryPojoToInventoryData;

@Service
public class InventoryDto {

    @Autowired
    private ProductService ps;

    @Autowired
    private InventoryService service;


    public void add( InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);

        inventoryPojo.setProductId(ps.extractProd_Id(inventoryForm.getBarcode()));

        service.add(inventoryPojo);
    }
    public void delete(Integer id) {
        service.delete(id);
    }

    public InventoryData get( Integer id) throws ApiException {
        InventoryPojo inventoryPojo = service.get(id);
        return convertInventoryPojoToInventoryData(inventoryPojo);
    }


    public List<InventoryData> getAll() {
        List<InventoryPojo> inventoryPojoList = service.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            inventoryDataList.add(convertInventoryPojoToInventoryData(inventoryPojo));
        }
        return inventoryDataList;
    }

    public void update( Integer id, InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);
        service.update(id, inventoryPojo);
    }
}
