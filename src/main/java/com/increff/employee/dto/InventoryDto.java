package com.increff.employee.dto;


import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    private ProductService ps;

    @Autowired
    private InventoryService service;


    public void add( InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        service.add(p);
    }
    public void delete(int id) {
        service.delete(id);
    }

    public InventoryData get( int id) throws ApiException {
        InventoryPojo p = service.get(id);
        return convert(p);
    }


    public List<InventoryData> getAll() {
        List<InventoryPojo> list = service.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update( int id, InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.update(id, p);
    }


    private static InventoryData convert(InventoryPojo p) {
        InventoryData d = new InventoryData();
        d.setid(p.getId());
        d.setProduct_id(p.getProduct_id());
        d.setQuantity(p.getQuantity());
        return d;
    }

    private InventoryPojo convert(InventoryForm f) throws ApiException {
        InventoryPojo p = new InventoryPojo();
        p.setProduct_id(ps.extractProd_Id(f.getBarcode()));
        p.setQuantity(f.getQuantity());
        return p;
    }

}
