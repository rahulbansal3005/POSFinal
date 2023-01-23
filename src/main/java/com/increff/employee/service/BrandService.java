package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
@Service
public class BrandService {

    @Autowired
    private BrandDao dao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, BrandPojo brandPojo) throws ApiException {
        BrandPojo newbrandPojo = getCheck(id);
        newbrandPojo.setCategory(brandPojo.getCategory());
        newbrandPojo.setBrand(brandPojo.getBrand());
    }

    public List<BrandPojo> getCategory(String brand) throws ApiException {
        return dao.getCategory(brand);
    }
    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }


    @Transactional
    public int extractId(ProductForm f) throws ApiException {
        BrandPojo p = dao.getBrand_category(f);
        if (p == null) {
            throw new ApiException("Brand-Category combination does not exist ");
        }
        return p.getId();
    }
}
