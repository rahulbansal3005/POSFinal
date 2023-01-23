package com.increff.employee.dto;


import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.employee.util.Normalize.normalize;
import static com.increff.employee.util.helper.convertBrandFormToBrandPojo;
import static com.increff.employee.util.helper.convertBrandPojoToBrandData;

@Service
public class BrandDto {
    @Autowired
    private BrandService service;

//    TODO in util helper convert functions.  DONE
//    TODO in validate, in Normalize.  DONE

//    TODO variable names make meaningful  DONE
//    TODO normalize in DTO not in service, normalize forms not pojos.  DONE
//    TODO validation in DTO not in service  DONE
    public void add(BrandForm brandForm) throws ApiException {
        normalize(brandForm); // Normalize Forms and not Pojos.
        BrandPojo brandPojo = convertBrandFormToBrandPojo(brandForm);
        if (Validate.isEmpty(brandPojo.getBrand())) {
            throw new ApiException("brand name cannot be null or empty");
        }

        if (Validate.isEmpty(brandPojo.getCategory())) {
            throw new ApiException("category name cannot be null or empty");
        }
        service.add(brandPojo);
    }

//    TODO remove delete
    public void delete(int brandId) {
        service.delete(brandId);
    }

    public BrandData get(int brandId) throws ApiException {
        BrandPojo brandPojo = service.get(brandId);
        return convertBrandPojoToBrandData(brandPojo);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> brandPojoList = service.getAll();
        List<BrandData> brandDataList = new ArrayList<BrandData>();
        for (BrandPojo p : brandPojoList) {
            brandDataList.add(convertBrandPojoToBrandData(p));
        }
        return brandDataList;
    }

//TODO id->brandID  DONE
    public void update(int brandId,BrandForm brandForm) throws ApiException {
        normalize(brandForm);
        BrandPojo brandPojo = convertBrandFormToBrandPojo(brandForm);
        service.update(brandId, brandPojo);
    }

    public List<String> getCategory(String categ) throws ApiException {
        if(Validate.isEmpty(categ)){
            throw new ApiException("brand name cannot be empty");
        }
        List<BrandPojo> brandPojoList = service.getCategory(categ);
        List<String> stringList = new ArrayList<String>();
        for(BrandPojo brandPojo : brandPojoList){
            stringList.add(brandPojo.getCategory());
        }
        return stringList;
    }

    public List<String> getAllUniqueBrands() throws ApiException {
        List<BrandPojo> brandPojoList = service.getAll();
        Set set = new HashSet();

        for (BrandPojo brandPojo : brandPojoList) {
            set.add(brandPojo.getBrand());
        }

        List<String> stringList = new ArrayList<>(set);
        return stringList;
    }
}
