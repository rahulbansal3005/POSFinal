package com.increff.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.util.Normalize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.Form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
@Service
public class BrandService {

    @Autowired
    private BrandDao dao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo brandPojo) throws ApiException {
        dao.insert(brandPojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, BrandPojo brandPojo) throws ApiException {
        BrandPojo newbrandPojo = getCheck(id);
        newbrandPojo.setCategory(brandPojo.getCategory());
        newbrandPojo.setBrand(brandPojo.getBrand());
    }

    public List<BrandPojo> getCategory(String brand) throws ApiException {
        return dao.getCategory(brand);
    }
    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = dao.select(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
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
        BrandPojo brandPojo = dao.getBrand_category(productForm);
        if (brandPojo == null) {
            throw new ApiException("Brand-Category combination does not exist ");
        }
        return brandPojo.getId();
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> searchBrandCategoryData(BrandForm brandForm) throws ApiException {
//        Validate.checkBrandCategory(brandForm);
        Normalize.normalizeBrandForm(brandForm);
        List<BrandPojo> brandPojoList;
        if(brandForm.getBrand()=="" && brandForm.getCategory()=="")
        {
            brandPojoList=dao.selectAll();
        }
        else if(brandForm.getBrand()=="")
        {
            brandPojoList=dao.selectByCategory(brandForm.getCategory());
        }
        else if(brandForm.getCategory()=="")
        {
            brandPojoList=dao.selectByBrand(brandForm.getBrand());
        }
        else
            brandPojoList=dao.searchBrandData(brandForm.getBrand(),brandForm.getCategory());

        if(brandPojoList.size()==0)
            throw new ApiException("Brand-Category Pair does not exist");
        return brandPojoList;
    }
//    @Transactional(rollbackOn = ApiException.class)
//    public List<BrandPojo> getByNameCategory(String brand, String category) {
//        if(Objects.equals(brand, "") && Objects.equals(category, "")) {
//            return dao.selectAll();
//        }
//        if(Objects.equals(brand, "")) {
//            return dao.selectByCategory(category);
//        }
//        if(Objects.equals(category, "")) {
//            return dao.selectByBrand(brand);
//        }
//
//        List<BrandPojo> brands = new ArrayList<>();
//        brands.add(dao.select(brand, category));
//        return brands;
//    }
}
