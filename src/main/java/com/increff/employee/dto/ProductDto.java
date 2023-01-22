package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private BrandService bs;
    @Autowired
    private ProductService service;

    public void add(ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        service.add(p);
    }
    public void delete( int id) {
        service.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = service.get(id);
        return convert(p);
    }

    public List<ProductData> getAll() {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }


    public void update(int id, ProductForm f) throws ApiException {
        ProductPojo p = convert(f);
        service.update(id, p);
    }

//    Conversion Functions
    private static ProductData convert(ProductPojo p) {
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setBrand_category(p.getBrand_category());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        return d;
    }

    private ProductPojo convert(ProductForm f) throws ApiException {
        ProductPojo p = new ProductPojo();
        p.setBarcode(f.getBarcode());
        p.setBrand_category(bs.extractId(f));
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }
}
