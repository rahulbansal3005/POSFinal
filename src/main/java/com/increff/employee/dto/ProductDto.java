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


@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    public void add(ProductForm productForm) throws ApiException {

        if (Validate.isEmpty(productForm.getName())) {
            throw new ApiException("Name cannot be empty or null");
        }if (Validate.isEmpty(productForm.getBarcode())) {
            throw new ApiException("Barcode cannot be empty or null");
        }if (Validate.isEmpty(productForm.getBrand())) {
            throw new ApiException("Brand cannot be empty or null");
        }if (Validate.isEmpty(productForm.getCategory())) {
            throw new ApiException("Category cannot be null or empty");
        }if (productForm.getMrp()<0) {
            throw new ApiException("MRP cannot be negative");
        }if (productForm.getMrp()==null) {
            throw new ApiException("MRP cannot be empty");
        }

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
        return convertProductPojoToProductData(productPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojoList = productService.getAll();
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
        productService.update(id, productPojo);
    }

}
