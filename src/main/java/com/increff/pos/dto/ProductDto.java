package com.increff.pos.dto;

import com.increff.pos.model.Data.ProductData;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.Normalize;
import com.increff.pos.util.Validate;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.Helper.convertProductFormToProductPojo;
import static com.increff.pos.util.Helper.convertProductPojoToProductData;
import static com.increff.pos.util.Validate.validateproductFormonAdd;


@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;

    public void add(ProductForm productForm) throws ApiException {
        validateproductFormonAdd(productForm);
        Normalize.normalizeProductForm(productForm);
        ProductPojo productPojo=productService.getCheck(productForm.getBarcode());
        if(productPojo!=null)
            throw new ApiException("Barcode already present");
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
        Normalize.normalizeProductForm(productForm);
//        ProductPojo productPojo = convertProductFormToProductPojo(productForm);

        productService.update(id, productForm.getName(),productForm.getMrp());
    }

    public void addBulk(ProductForm[] productForms) throws ApiException {
            JSONArray array = new JSONArray();

//        Check for duplicates in the list.
            Validate.checkDuplicateProduct(productForms,array);
//        Check barcodes in DB
        productService.getCheckProductsInBulk(productForms,array);
            for(ProductForm productForm:productForms)
            {
                Validate.ValidateProductFormForBulkAdd(productForm,array);
                Normalize.NormalizeProductFormForbulkAdd(productForm);
//                check brand and category name in db
                brandService.checkForNameCategoryForBulk(productForm.getBrand(),productForm.getCategory(),array);
            }
            if(array.length()!=0)
            {
                throw new ApiException(array.toString());
            }
            for(ProductForm productForm:productForms)
            {
                add(productForm);
            }

    }
}
