package com.increff.pos.dto;

import com.increff.pos.model.Data.ProductData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.Validate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;


    @Test
    public void testValidateProductForm() throws ApiException {
        ProductForm productForm = new ProductForm();
        try {
            Validate.validateproductFormonAdd(productForm);
        } catch (ApiException e) {
            assertEquals("Name cannot be empty or null", e.getMessage());
        }
        productForm.setName("a");
        try {
            Validate.validateproductFormonAdd(productForm);
        } catch (ApiException e) {
            assertEquals("Barcode cannot be empty or null", e.getMessage());
        }
        productForm.setBarcode("a");
        try {
            Validate.validateproductFormonAdd(productForm);
        } catch (ApiException e) {
            assertEquals("Brand cannot be empty or null", e.getMessage());
        }
        productForm.setBrand("a");
        try {
            Validate.validateproductFormonAdd(productForm);
        } catch (ApiException e) {
            assertEquals("Category cannot be null or empty", e.getMessage());
        }
        productForm.setCategory("a");
        try {
            Validate.validateproductFormonAdd(productForm);
        } catch (ApiException e) {
            assertEquals("MRP cannot be empty", e.getMessage());
        }
        productForm.setMrp(1.0);
    }

    @Test
    public void testAdd() throws ApiException {
        ProductForm productForm = new ProductForm();
        productForm.setBarcode("a");
        productForm.setBrand("b");
        productForm.setCategory("c");
        productForm.setName("d");
        productForm.setMrp(1.0);
        try {
            productDto.add(productForm);
        }
        catch (ApiException e) {
            assertEquals("Brand-Category combination does not exist ", e.getMessage());
        }

        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("b");
        brandForm.setCategory("c");
        brandDto.add(brandForm);
        productDto.add(productForm);

        ProductData productData = productDto.getAll().get(0);
        assertEquals("a", productData.getBarcode());
        assertEquals("b", productData.getBrand());
        assertEquals("c", productData.getCategory());
        assertEquals("d", productData.getName());
        assertEquals(1, productData.getMrp(), 0.01);
    }

//    @Test
//    public void testDelete() throws ApiException {
//        productDto.delete(1);
//    }

    @Test
    public void testBulkAdd() throws ApiException{
        List<ProductForm> productForms=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            ProductForm productForm = new ProductForm();
            productForm.setBarcode("a"+i);
            productForm.setBrand("b"+i);
            productForm.setCategory("c"+i);
            productForm.setName("d"+i);
            productForm.setMrp(1.0);
            try {
                productDto.add(productForm);
            }
            catch (ApiException e) {
                assertEquals("Brand-Category combination does not exist ", e.getMessage());
            }

            BrandForm brandForm = new BrandForm();
            brandForm.setBrand("b"+i);
            brandForm.setCategory("c"+i);
            brandDto.add(brandForm);
            productForms.add(productForm);
        }
        productDto.addBulk(productForms);

        List<ProductData> productDataList = productDto.getAll();
        assertEquals(5, productDataList.size());

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

        int id = productDto.getAll().get(0).getId();

        ProductData productData = productDto.get(id);

        ProductForm productForm1 = new ProductForm();
        productForm1.setBarcode("a");
        productForm1.setBrand("b");
        productForm1.setCategory("c");
        productForm1.setName("e");
        productForm1.setMrp(100.004);
        productDto.update(id, productForm1);

        ProductData productDataUpdated = productDto.getAll().get(0);
        assertEquals(id, productDataUpdated.getId());
        assertEquals(productForm1.getBrand(), productDataUpdated.getBrand());
        assertEquals(productForm1.getMrp(), productDataUpdated.getMrp(), 0.01);
        assertEquals(productForm1.getBarcode(), productDataUpdated.getBarcode());
        assertEquals(productForm1.getName(), productDataUpdated.getName());
    }

}
