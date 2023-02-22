package com.increff.pos.dto;

import com.increff.pos.model.Data.BrandData;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.service.AbstractUnitTest;

import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class BrandDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandDto brandDto;



    @Test
    public void testAdd() throws Exception {

        brandDto.add(TestHelper.brandForm("brand1","category1"));
        assertEquals(1,brandDto.getAll().size());
    }
    @Test
    public void testGetAll() throws ApiException {
        brandDto.add(TestHelper.brandForm("adidas","shoes"));
        brandDto.add(TestHelper.brandForm("adidas","wears"));
        List<BrandData> brandDataList = brandDto.getAll();
        assertEquals(2, brandDataList.size());
    }

//    @Test
//    public void testDelete() throws Exception {
//        brandDto.add(TestHelper.brandForm("brand1","category1"));
//        brandDto.delete(1);
//    }

    @Test
    public void testUpdate() throws ApiException {
        brandDto.add(TestHelper.brandForm("adidas","shoes"));
        int id = brandDto.getAll().get(0).getId();
        BrandData brandData = brandDto.get(id);

        BrandForm brandForm1 = new BrandForm();
        brandForm1.setCategory("sneakers");
        brandForm1.setBrand("adidas");
        brandDto.update(id, brandForm1);

        BrandData brandDataUpdated = brandDto.getAll().get(0);
        assertEquals(id, brandDataUpdated.getId());
        assertEquals(brandForm1.getBrand(), brandDataUpdated.getBrand());
        assertEquals(brandForm1.getCategory(), brandDataUpdated.getCategory());
    }
    @Test
    public void testBulkAdd() throws ApiException{
        List<BrandForm> brandForms=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            brandForms.add(TestHelper.brandForm("brand"+i,"category"+i));
        }
        brandDto.bulkAdd(brandForms);

        List<BrandData> brandDataList = brandDto.getAll();
        assertEquals(5, brandDataList.size());
    }

//    @Test
//    public void testGetUniqueBrandsAndCategory() throws ApiException {
//        List<BrandForm> brandForms=new ArrayList<>();
//        for(int i=0;i<5;i++)
//        {
//            brandForms.add(TestHelper.brandForm("brand"+i,"category"+i));
//        }
//        brandDto.bulkAdd(brandForms);
//        List<String> stringList=brandDto.getAllUniqueBrands();
//        assertEquals(5, stringList.size());
//        List<String> stringList1=brandDto.getCategory("adidas1");
//        assertEquals(0, stringList1.size());
//    }

}
