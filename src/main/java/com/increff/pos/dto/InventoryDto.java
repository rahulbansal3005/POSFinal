package com.increff.pos.dto;


import com.increff.pos.model.Data.InventoryData;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.Validate;
//import java.util.regex.matches;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.Helper.*;
import static com.increff.pos.util.Normalize.normalizeInventoryForm;

@Service
public class InventoryDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;


    public void add(InventoryForm inventoryForm) throws ApiException {

        Validate.validateInventoryFormonAdd(inventoryForm);
        normalizeInventoryForm(inventoryForm);

        int prodId = productService.extractProductId(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = inventoryService.selectOnProdId(prodId);
        if (inventoryPojo != null) {
            inventoryService.updateIventory(inventoryPojo.getId(), inventoryForm.getQuantity());
        } else {
            inventoryPojo = convertInventoryFormToInventoryPojo(inventoryForm);
            inventoryPojo.setProductId(prodId);
            inventoryService.add(inventoryPojo);
        }
    }

    public InventoryData get(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(id);
        InventoryData inventoryData = convertInventoryPojoToInventoryData(inventoryPojo);
        inventoryData.setBarcode(productService.extractBarCode(inventoryPojo.getProductId()));
        return inventoryData;
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : inventoryPojoList) {
            InventoryData inventoryData = convertInventoryPojoToInventoryData(inventoryPojo);
            System.out.println(inventoryPojo.getProductId());
            inventoryData.setBarcode(productService.extractBarCode(inventoryPojo.getProductId()));
            inventoryDataList.add(inventoryData);
        }
        return inventoryDataList;
    }

    public void update(Integer id, InventoryForm inventoryForm) throws ApiException {
        Validate.validateInventoryFormonUpdate(inventoryForm);
        inventoryService.update(id, inventoryForm.getQuantity());
    }

    public void addBulk(List<InventoryForm> inventoryForms) throws ApiException {
        if(inventoryForms.size()>5000)
            throw new ApiException("File size is larger than 5000");
        JSONArray array = new JSONArray();
        int index=1;
        for (InventoryForm inventoryForm : inventoryForms) {

            //        check for empty form
            Validate.ValidateInventoryFormForBulkAdd(inventoryForm,array,index);

            ProductPojo productPojo=productService.getCheck(inventoryForm.getBarcode());
            if(productPojo==null)
            {
                createInventoryErrorobject(inventoryForm,array,"no product exist with this barcode with row number: "+index);
            }
            index++;
        }
        if (array.length() != 0) {
            throw new ApiException(array.toString());
        }
        for (InventoryForm inventoryForm : inventoryForms) {
            add(inventoryForm);
        }
    }
}
