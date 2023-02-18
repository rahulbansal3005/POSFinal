package com.increff.pos.dto;

import com.increff.pos.model.Data.InventoryData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest  extends AdminDtoTest{
    @Autowired
    private InventoryDto inventoryDto;

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;


    @Test
    public void testAdd() throws ApiException {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("a");
        inventoryForm.setQuantity(49);
        try{
            inventoryDto.add(inventoryForm);
        }
        catch(ApiException e) {
            assertEquals("Product with given barcode does not exist, barcode: a", e.getMessage());
        }
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("b");
        brandForm.setCategory("c");
        brandDto.add(brandForm);

        ProductForm productForm = new ProductForm();
        productForm.setBarcode("a");
        productForm.setBrand("b");
        productForm.setCategory("c");
        productForm.setName("d");
        productForm.setMrp(1.00);
        productDto.add(productForm);
        inventoryDto.add(inventoryForm);

        InventoryData inventoryData = inventoryDto.getAll().get(0);
        assertEquals("a", inventoryData.getBarcode());
        assertEquals(Optional.of(49), inventoryData.getQuantity());
    }

    @Test
    public void testDelete() throws ApiException {
        inventoryDto.delete(1);
    }

    @Test
    public void testGetAll() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("b");
        brandForm.setCategory("c");
        brandDto.add(brandForm);

        ProductForm productForm = new ProductForm();
        productForm.setBarcode("a");
        productForm.setBrand("b");
        productForm.setCategory("c");
        productForm.setName("d");
        productForm.setMrp(1.000);
        productDto.add(productForm);

        productForm.setBarcode("b");
        productDto.add(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("a");
        inventoryForm.setQuantity(49);
        inventoryDto.add(inventoryForm);

        InventoryForm inventoryForm1 = new InventoryForm();
        inventoryForm1.setBarcode("b");
        inventoryForm1.setQuantity(99);
        inventoryDto.add(inventoryForm1);

        List<InventoryData> inventoryDataList = inventoryDto.getAll();
        assertEquals(2, inventoryDataList.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("b");
        brandForm.setCategory("c");
        brandDto.add(brandForm);

        ProductForm productForm = new ProductForm();
        productForm.setBarcode("a");
        productForm.setBrand("b");
        productForm.setCategory("c");
        productForm.setName("d");
        productForm.setMrp(1.0);
        productDto.add(productForm);

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("a");
        inventoryForm.setQuantity(49);
        inventoryDto.add(inventoryForm);

        int id = inventoryDto.getAll().get(0).getId();

        InventoryData inventoryData = inventoryDto.get(id);

        InventoryForm inventoryForm1 = new InventoryForm();
        inventoryForm1.setBarcode("a");
        inventoryForm1.setQuantity(99);
        inventoryDto.update(id, inventoryForm1);

        InventoryData inventoryDataUpdated = inventoryDto.getAll().get(0);
        assertEquals(Optional.of(id), inventoryDataUpdated.getId());
        assertEquals(inventoryForm1.getBarcode(), inventoryDataUpdated.getBarcode());
        assertEquals(inventoryForm1.getQuantity(), inventoryDataUpdated.getQuantity());
    }
}
