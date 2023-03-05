package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.model.Form.BrandForm;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.BrandPojo;

import static com.increff.pos.util.Helper.createBrandErrorobject;
import static com.increff.pos.util.Helper.createProductErrorobject;

@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo brandPojo) throws ApiException {
        brandDao.insert(brandPojo);
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void delete(Integer id) {
//        brandDao.delete(id);
//    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, BrandPojo brandPojo) throws ApiException {
//        TODO
        BrandPojo newbrandPojo = getCheck(id);
        newbrandPojo.setCategory(brandPojo.getCategory());
        newbrandPojo.setBrand(brandPojo.getBrand());
    }

    public List<BrandPojo> getCategory(String brand) {
        return brandDao.getCategory(brand);
    }
    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = brandDao.select(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exist, id: " + id);
        }
        return brandPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public String getBrandName(Integer id) throws ApiException {
        BrandPojo brandPojo=get(id);
        return brandPojo.getBrand();
    }

    @Transactional(rollbackOn = ApiException.class)
    public String getCategoryName(Integer id) throws ApiException {
        BrandPojo brandPojo=get(id);
        return brandPojo.getCategory();
    }


    @Transactional(rollbackOn = ApiException.class)
    public int extractId(ProductForm productForm) throws ApiException {
        BrandPojo brandPojo = brandDao.getBrand_category(productForm);
        if (brandPojo == null) {
            throw new ApiException("Brand-Category combination does not exist ");
        }
        return brandPojo.getId();
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> searchBrandCategoryData(BrandForm brandForm) throws ApiException {
        List<BrandPojo> brandPojoList;
        if(brandForm.getBrand()=="" && brandForm.getCategory()=="")
        {
            brandPojoList= brandDao.selectAll();
        }
        else if(brandForm.getBrand()=="")
        {
            brandPojoList= brandDao.selectByCategory(brandForm.getCategory());
        }
        else if(brandForm.getCategory()=="")
        {
            brandPojoList= brandDao.selectByBrand(brandForm.getBrand());
        }
        else
            brandPojoList= brandDao.searchBrandData(brandForm.getBrand(),brandForm.getCategory());

        if(brandPojoList.size()==0)
            throw new ApiException("Brand-Category Pair does not exist");
        return brandPojoList;
    }


//    @Transactional(rollbackOn = ApiException.class)
//    public List<BrandPojo> getBrandPojosOnCategoryName(String category) throws ApiException {
//        List<BrandPojo> brandPojoList= brandDao.selectByCategory(category);
//        if(brandPojoList.size()==0)
//            throw new ApiException("Brand-Category Pair does not exist");
//        return brandPojoList;
//
//    }


//    @Transactional(rollbackOn = ApiException.class)
//    public List<BrandPojo> getBrandPojosOnBrandName(String brand) throws ApiException {
//        List<BrandPojo> brandPojoList= brandDao.selectByBrand(brand);
//        if(brandPojoList.size()==0)
//            throw new ApiException("Brand-Category Pair does not exist");
//        return brandPojoList;
//    }
    @Transactional(rollbackOn = ApiException.class)
    public void getByNameCategory(String brand, String category) throws ApiException {

        BrandPojo brandPojo=brandDao.selectonBrandCategory(brand, category);
        if(brandPojo!=null)
            throw new ApiException("Brand-Category already existed");
        return;
    }

    public BrandPojo searchBrandCategory(BrandForm brandForm) {

        BrandPojo brandPojo= brandDao.selectonBrandCategory(brandForm.getBrand(),brandForm.getCategory());
        return brandPojo;
    }

//
    public void getByNameCategoryForBulk(String brand, String category, JSONArray array,int index) {
        BrandPojo brandPojo=brandDao.selectonBrandCategory(brand, category);
        if(brandPojo!=null)
            createBrandErrorobject(brand,category,array,"brand-category pair already exist row number: "+ index);
    }

    public void checkForNameCategoryForBulk(ProductForm productForm, JSONArray array, int index) {
        BrandPojo brandPojo=brandDao.selectonBrandCategory(productForm.getBrand(), productForm.getCategory());
        if(brandPojo==null)
            createProductErrorobject(productForm,array,"brand-category does not exist with row number: "+index);
    }
}
