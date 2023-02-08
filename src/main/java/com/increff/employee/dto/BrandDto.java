package com.increff.employee.dto;


import com.increff.employee.model.Data.BrandData;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.Normalize;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.employee.util.Normalize.normalize;
import static com.increff.employee.util.Helper.convertBrandFormToBrandPojo;
import static com.increff.employee.util.Helper.convertBrandPojoToBrandData;

@Service
public class BrandDto {
    @Autowired
    private BrandService brandService;

    public void add(BrandForm brandForm) throws ApiException {
//        TODO change validate name.
//
        Validate.BrandForm(brandForm);
        Normalize.normalize(brandForm);                         // Normalize Forms and not Pojos.
        brandService.getByNameCategory(brandForm.getBrand(),brandForm.getCategory());
        BrandPojo brandPojo = convertBrandFormToBrandPojo(brandForm);
        brandService.add(brandPojo);
    }
//Todo remove delete
    public void delete(Integer brandId) {
        brandService.delete(brandId);
    }

    public BrandData get(Integer brandId) throws ApiException {
        BrandPojo brandPojo = brandService.get(brandId);
        return convertBrandPojoToBrandData(brandPojo);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandDataList.add(convertBrandPojoToBrandData(brandPojo));
        }
        return brandDataList;
    }

    public void update(Integer brandId,BrandForm brandForm) throws ApiException {
        Validate.BrandForm(brandForm);
        normalize(brandForm);
//        TODO check for existing pojo in the database.
        BrandPojo brandPojo =brandService.searchBrandCategory(brandForm);
        if(brandPojo!=null)
            throw new ApiException("Brand-Category already existed");

        BrandPojo brandPojo1 = convertBrandFormToBrandPojo(brandForm);
        brandService.update(brandId, brandPojo1);
    }

    public List<String> getCategory(String category) throws ApiException {
        if(Validate.isEmpty(category)){
            throw new ApiException("Brand name cannot be empty");
        }
        List<BrandPojo> brandPojoList = brandService.getCategory(category);
        List<String> stringList = new ArrayList<String>();
        for(BrandPojo brandPojo : brandPojoList){
            stringList.add(brandPojo.getCategory());
        }
        return stringList;
    }

    public List<String> getAllUniqueBrands() throws ApiException {
        List<BrandPojo> brandPojoList = brandService.getAll();
        Set set = new HashSet();

        for (BrandPojo brandPojo : brandPojoList) {
            set.add(brandPojo.getBrand());
        }

        List<String> stringList = new ArrayList<>(set);
        return stringList;
    }
}
