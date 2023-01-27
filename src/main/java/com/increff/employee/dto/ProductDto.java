package com.increff.employee.dto;

import com.increff.employee.model.Data.ProductData;
import com.increff.employee.model.Form.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.Normalize.normalize;
import static com.increff.employee.util.helper.convertProductFormToProductPojo;
import static com.increff.employee.util.helper.convertProductPojoToProductData;


@Service
public class ProductDto {
    @Autowired
    private ProductService service;
    @Autowired
    private BrandService brandService;

    public void add(ProductForm productForm) throws ApiException {
        normalize(productForm);
        ProductPojo productPojo = convertProductFormToProductPojo(productForm);
        productPojo.setBrandCategory(brandService.extractId(productForm));
        if (Validate.isEmpty(productPojo.getName())) {
            throw new ApiException("name cannot be empty");
        }
        service.add(productPojo);
    }
    public void delete( Integer id) {
        service.delete(id);
    }

    public ProductData get(Integer id) throws ApiException {
        ProductPojo productPojo = service.get(id);
        return convertProductPojoToProductData(productPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojoList = service.getAll();
        List<ProductData> productDataList = new ArrayList<ProductData>();
        for (ProductPojo productPojo : productPojoList) {
//            TODO use brandservice for brand and category name remove brandCategoryID
            ProductData productData =convertProductPojoToProductData(productPojo);
            productData.setBrand(brandService.getBrandName(productData.getId()));
            productData.setCategory(brandService.getCategoryName(productData.getId()));
            productDataList.add(productData);
        }
        return productDataList;
    }


    public void update(Integer id, ProductForm productForm) throws ApiException {
        normalize(productForm);
        ProductPojo productPojo = convertProductFormToProductPojo(productForm);
        service.update(id, productPojo);
    }

}
