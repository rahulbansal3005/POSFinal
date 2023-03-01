package com.increff.pos.service;

import com.increff.pos.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BrandServiceTest extends AbstractUnitTest{
    @Autowired
    private BrandService brandService;

    @Test
    public void testAdd() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("shirt");
        //Check whether brand and category is added
        brandService.add(brandPojo);

    }


//    @Test
//    public void testDelete() throws ApiException {
//        //
//        brandService.delete(1);
//    }

    @Test
    public void testGet() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("adidas");
        brandPojo.setCategory("shoes");
        //adding a row
        brandService.add(brandPojo);
        int id = brandService.getAll().get(0).getId();
        brandPojo = brandService.get(id);
        String brand = brandPojo.getBrand();
        String category = brandPojo.getCategory();
        assertEquals("adidas", brand);
        assertEquals("shoes", category);


        try{
            String brand2=brandService.getBrandName(id);
        }
        catch (ApiException e)
        {
            assertEquals("Brand with given ID does not exist, id: "+id, e.getMessage());
        }

        try{
            String category2=brandService.getCategoryName(id);
        }
        catch (ApiException e)
        {
            assertEquals("Brand with given ID does not exist, id: "+id, e.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("adidas");
        brandPojo.setCategory("shoes");
        //adding one row
        brandService.add(brandPojo);
        //get list of all pojo
        List<BrandPojo> brandPojoList = brandService.getAll();
        //check size and isEmpty()
        assertFalse(brandPojoList.isEmpty());
        assertEquals(brandPojoList.size(), 1);
    }

    @Test
    public void testUpdate() throws ApiException {
        //add
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("shirt");
        brandService.add(brandPojo);
        //update
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setBrand("spykar");
        brandPojo1.setCategory("shirts");
        brandService.update(brandPojo.getId(), brandPojo1);
        //checking updated pojo
        BrandPojo updatedBrandPojo = brandService.get(brandPojo.getId());
        assertEquals(updatedBrandPojo.getBrand(), "spykar");
        assertEquals(updatedBrandPojo.getCategory(), "shirts");
    }

    @Test
    public void testGetCheck() throws ApiException {
        try {
            brandService.getCheck(1);
        } catch (ApiException e) {
            assertEquals("Brand with given ID does not exist, id: 1", e.getMessage());
        }

    }

    @Test
    public void testCheckUnique() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("shirt");
        //adding test pojo
        brandService.add(brandPojo);
        try {
            //adding the same pojo
            BrandPojo brandPojo1 = new BrandPojo();
            brandPojo1.setBrand("puma");
            brandPojo1.setCategory("shirt");
            brandService.add(brandPojo1);
        } catch (ApiException e) {
            assertEquals("Brand category combination must be unique", e.getMessage());
        }

    }

    @Test
    public void testSelectByBrandCategory() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("shirt");
        brandService.add(brandPojo);
            BrandPojo brandPojo1 = brandService.searchBrandCategory(TestHelper.brandForm("puma", "trouser"));
            assertEquals(null,brandPojo1);
        BrandPojo brandPojo2 = brandService.searchBrandCategory(TestHelper.brandForm("puma", "pant"));
    }

//    @Test
//    public void testSelectByBrand() throws ApiException {
//        try {
//            List<BrandPojo> brandPojoList=brandService.getBrandPojosOnBrandName("puma");
//        }
//        catch (ApiException e)
//        {
//            assertEquals("Brand-Category Pair does not exist", e.getMessage());
//        }
//    }

//    @Test
//    public void testSelectByCategory() throws ApiException {
//        try {
//            List<BrandPojo> brandPojoList=brandService.getBrandPojosOnCategoryName("shirt");
//        }
//        catch (ApiException e)
//        {
//            assertEquals("Brand-Category Pair does not exist", e.getMessage());
//        }
//    }

    @Test
    public void testSelectById() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("shirt");
        brandService.add(brandPojo);
        try{
            brandService.getCheck(2);
        }
        catch (ApiException e) {
            assertEquals("Brand with given ID does not exist, id: 2", e.getMessage());
        }
        BrandPojo brandPojo1 = brandService.searchBrandCategory(TestHelper.brandForm("puma", "shirt"));
        brandService.getCheck(brandPojo1.getId());
    }


    @Test
    public void testgetCategory()
    {
            List<BrandPojo> brandPojoList=brandService.getCategory("qwerty");
            assertEquals(0,brandPojoList.size());
    }

    @Test
    public void testsearchBrandCategoryData(){
        try{
            List<BrandPojo>brandPojoList=brandService.searchBrandCategoryData(TestHelper.brandForm("puma","shoes"));
        }
        catch (ApiException e)
        {
            assertEquals("Brand-Category Pair does not exist", e.getMessage());
        }
    }

    @Test
    public void testgetByNameCategory() throws ApiException {
        try{
            brandService.getByNameCategory("puma","shoes");
//            brandService.getByNameCategory("","shoes");
//            brandService.getByNameCategory("puma","");
//            brandService.getByNameCategory("","");

        }
        catch (ApiException e){
            assertEquals("Brand-Category already existed",e.getMessage());
        }
    }

    @Test
    public void testgetByNameCategory2() throws ApiException {
        try{
//            brandService.getByNameCategory("puma","shoes");
            brandService.getByNameCategory("","shoes");
//            brandService.getByNameCategory("puma","");
//            brandService.getByNameCategory("","");

        }
        catch (ApiException e){
            assertEquals("Brand-Category already existed",e.getMessage());
        }
    }

    @Test
    public void testgetByNameCategory3() throws ApiException {
        try{
//            brandService.getByNameCategory("puma","shoes");
//            brandService.getByNameCategory("","shoes");
            brandService.getByNameCategory("puma","");
//            brandService.getByNameCategory("","");

        }
        catch (ApiException e){
            assertEquals("Brand-Category already existed",e.getMessage());
        }
    }

    @Test
    public void testgetByNameCategory4() throws ApiException {
        try{
//            brandService.getByNameCategory("puma","shoes");
//            brandService.getByNameCategory("","shoes");
//            brandService.getByNameCategory("puma","");
            brandService.getByNameCategory("","");

        }
        catch (ApiException e){
            assertEquals("Brand-Category already existed",e.getMessage());
        }
    }

//    @Test
//    public void testgetByNameCategoryForBulk()

    @Test
    public void testextractId(){
        try{
            int id=brandService.extractId(TestHelper.addProduct("12","puma","shoes","were",12.00));
        }
        catch (ApiException e)
        {
            assertEquals("Brand-Category combination does not exist ", e.getMessage());
        }
    }
}
