package com.increff.employee.dto;


import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BrandDto {
    @Autowired
    private BrandService service;

    public void add(BrandForm form) throws ApiException {
        BrandPojo p = convert(form);
        service.add(p);
    }
    public void delete(int id) {
        service.delete(id);
    }

    public BrandData get( int id) throws ApiException {
        BrandPojo p = service.get(id);
        return convert(p);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id,BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id, p);
    }



    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }

    private static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }

    public List<String> getCategory(String categ) throws ApiException {
        List<BrandPojo> list = service.getCategory(categ);
        List<String> list2 = new ArrayList<String>();
        for(BrandPojo b : list){
            list2.add(b.getCategory());
        }
        return list2;
    }

    public List<String> getAllUniqueBrands() throws ApiException {
        List<BrandPojo> list = service.getAll();
        Set s = new HashSet();

        for (BrandPojo p : list) {
            s.add(p.getBrand());
        }

        List<String> l = new ArrayList<>(s);
        return l;
    }
}
