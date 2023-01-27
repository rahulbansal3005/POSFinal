package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

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

    @Transactional
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
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
    @Transactional
    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = dao.select(id);
        if (brandPojo == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return brandPojo;
    }

    @Transactional
    public String getBrandName(Integer id) throws ApiException {
        BrandPojo brandPojo=get(id);
        return brandPojo.getBrand();
    }

    @Transactional
    public String getCategoryName(Integer id) throws ApiException {
        BrandPojo brandPojo=get(id);
        return brandPojo.getCategory();
    }


    @Transactional
    public int extractId(ProductForm productForm) throws ApiException {
        BrandPojo brandPojo = dao.getBrand_category(productForm);
        if (brandPojo == null) {
            throw new ApiException("Brand-Category combination does not exist ");
        }
        return brandPojo.getId();
    }
}
