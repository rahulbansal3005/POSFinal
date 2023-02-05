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
import static com.increff.employee.util.Helper.convertProductFormToProductPojo;
import static com.increff.employee.util.Helper.convertProductPojoToProductData;
import static com.increff.employee.util.Validate.validateproductFormonAdd;


@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    public void add(ProductForm productForm) throws ApiException {
        validateproductFormonAdd(productForm);
        normalize(productForm);
        ProductPojo productPojo=productService.getCheck(productForm.getBarcode());
        if(productPojo!=null)
            throw new ApiException("Barcode already present in the Database");
        productPojo = convertProductFormToProductPojo(productForm);
        productPojo.setBrandCategory(brandService.extractId(productForm));
        productService.add(productPojo);
    }
    public void delete( Integer id) {
        productService.delete(id);
    }

    public ProductData get(Integer id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        ProductData productData= convertProductPojoToProductData(productPojo);
        productData.setBrand(brandService.getBrandName(productData.getBrand_category()));
        productData.setCategory(brandService.getCategoryName(productData.getBrand_category()));
        return productData;
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojoList = productService.getAll();
        List<ProductData> productDataList = new ArrayList<ProductData>();
        for (ProductPojo productPojo : productPojoList) {
            ProductData productData =convertProductPojoToProductData(productPojo);
//            System.out.println(productData.getBrand_category());
//            System.out.println(brandService.getBrandName(productData.getBrand_category()));
//            System.out.println(brandService.getCategoryName(productData.getBrand_category()));


            productData.setBrand(brandService.getBrandName(productData.getBrand_category()));
            productData.setCategory(brandService.getCategoryName(productData.getBrand_category()));
            productDataList.add(productData);
        }
        return productDataList;
    }


    public void update(Integer id, ProductForm productForm) throws ApiException {
        Validate.validateProductFormOnUpdate(productForm);
        normalize(productForm);
//        ProductPojo productPojo = convertProductFormToProductPojo(productForm);

        productService.update(id, productForm.getName(),productForm.getMrp());
    }

}
