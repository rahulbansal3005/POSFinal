package com.increff.pos.dto;

import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.dto.TestHelper.addBrandToPojo;
import static com.increff.pos.dto.TestHelper.brandForm;
import static org.junit.Assert.assertEquals;

public class ReportDtoTest extends AbstractUnitTest {
    @Autowired
    private ReportDto reportDto;
    @Autowired
    private BrandDto brandDto;


    @Test
    public void testBrandReport() throws ApiException {
        List<BrandForm> brandForms=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            brandForms.add(TestHelper.brandForm("brand"+i,"category"+i));
        }
        brandDto.bulkAdd(brandForms);
        List<BrandForm> brandFormList=reportDto.getBrandReport(brandForm("",""));
        assertEquals(5, brandFormList.size());
        List<BrandForm> brandFormList2=reportDto.getBrandReport(brandForm("brand1",""));
        assertEquals(1, brandFormList2.size());
    }



    @Test
    public void testInventoryReport(){


    }



    @Test
    public void testDateWiseSalesReportReport(){

    }


    @Test
    public void testdailySalesReport(){

    }
}
