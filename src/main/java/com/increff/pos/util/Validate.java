package com.increff.pos.util;

import com.increff.pos.model.Data.OrderItem;
import com.increff.pos.model.Form.BrandForm;
import com.increff.pos.model.Form.InventoryForm;
import com.increff.pos.model.Form.ProductForm;
import com.increff.pos.model.Form.UserForm;
import com.increff.pos.service.ApiException;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.util.Helper.*;

public class Validate {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String toLowerCase(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    public static void checkOrderItem(OrderItem orderItem, List<String> errorMessages) {
        if(isEmpty(orderItem.getBarCode()))
        {
            String error = "BarCode can not be empty" + orderItem.getBarCode();
            errorMessages.add(error);
        }
        if (orderItem.getQuantity()==null || orderItem.getQuantity() < 0) {
            String error = "Invalid quantity of product with BarCode " + orderItem.getBarCode();
            errorMessages.add(error);
        }
        if (orderItem.getSellingPrice()==null || orderItem.getSellingPrice() < 0) {
            String error = "Invalid Selling Price of product with BarCode " + orderItem.getBarCode();
            errorMessages.add(error);
        }
    }

    public static void ContainDuplicates(List<OrderItem> orderForm, List<String> errorMessages) {
        Map<String, Integer> map = new HashMap<>();
        for (OrderItem orderItem : orderForm) {
            if (map.containsKey(orderItem.getBarCode())) {
                String error = "Duplicate product with BarCode " + orderItem.getBarCode();
                errorMessages.add(error);
            } else {
                map.put(orderItem.getBarCode(), 1);
            }
        }
    }

    public static void ValidateBrandForm(BrandForm brandForm) throws ApiException {
        if (Validate.isEmpty(brandForm.getBrand())) {
            throw new ApiException("Brand name cannot be null or empty");
        }
        if (Validate.isEmpty(brandForm.getCategory())) {
            throw new ApiException("Category name cannot be null or empty");
        }
    }

    public static void validateInventoryFormonAdd(InventoryForm inventoryForm) throws ApiException {
        if (isEmpty(inventoryForm.getBarcode()))
            throw new ApiException("BarCode is Null or Empty");
        if (inventoryForm.getQuantity() == null)
            throw new ApiException("Entered Quantity is NULL");
        if (inventoryForm.getQuantity() < 0)
            throw new ApiException("Entered Quantity is negative");
    }

    public static void validateInventoryFormonUpdate(InventoryForm inventoryForm) throws ApiException {
        System.out.println(inventoryForm.getBarcode());
        if (inventoryForm.getQuantity() == null)
            throw new ApiException("Entered Quantity is NULL");
        if (inventoryForm.getQuantity() < 0)
            throw new ApiException("Entered Quantity is negative");
    }

    public static void validateProductFormOnUpdate(ProductForm productForm) throws ApiException {
//		if(productForm.getName()=="" || productForm.getName()==null)
        if (isEmpty(productForm.getName()))
            throw new ApiException("Name Field is Empty or Null");
        if (productForm.getMrp() == null)
            throw new ApiException("MRP is not entered");
        if (productForm.getMrp() < 0)
            throw new ApiException("MRP entered is negative");
    }

    public static void validateproductFormonAdd(ProductForm productForm) throws ApiException {
        if (Validate.isEmpty(productForm.getName())) {
            throw new ApiException("Name cannot be empty or null");
        }
        if (Validate.isEmpty(productForm.getBarcode())) {
            throw new ApiException("Barcode cannot be empty or null");
        }
        if (Validate.isEmpty(productForm.getBrand())) {
            throw new ApiException("Brand cannot be empty or null");
        }
        if (Validate.isEmpty(productForm.getCategory())) {
            throw new ApiException("Category cannot be null or empty");
        }
        if (productForm.getMrp() == null) {
            throw new ApiException("MRP cannot be empty");
        }
        if (productForm.getMrp() < 0) {
            throw new ApiException("MRP cannot be negative");
        }

    }

    public static void validateUserForm(UserForm userForm) throws ApiException {
        if (userForm.getRole() == "")
            throw new ApiException("Please select a role");
        if (isEmpty(userForm.getEmail()))
            throw new ApiException("Email field is empty");
        if (isEmpty(userForm.getPassword()))
            throw new ApiException("Password field is empty");
        if (isEmpty(userForm.getRole()))
            throw new ApiException("Role field is empty");
    }

    public static void ValidateBrandFormForBulkAdd(BrandForm brandForm, JSONArray array, int index) {
        if (Validate.isEmpty(brandForm.getBrand()) || Validate.isEmpty(brandForm.getCategory())) {
            createBrandErrorobject(brandForm.getBrand(), brandForm.getCategory(), array, "fields are empty with row number: "+index);

        }
    }


    public static void checkDuplicateBrandform(List<BrandForm> brandForms, JSONArray array) throws ApiException {
        if(brandForms.size()>5000)
        {
            throw new ApiException("File size is larger than 5000");
        }
        Map<String, String> map = new HashMap<>();
        int index=1;
        for (BrandForm brandForm : brandForms) {
            if (map.containsKey(brandForm.getBrand())) {
                if ((map.get(brandForm.getBrand())).equals(brandForm.getCategory()))
                    createBrandErrorobject(brandForm.getBrand(), brandForm.getCategory(), array,"brand-category is duplicate with row number: "+index);
            } else {
                map.put(brandForm.getBrand(), brandForm.getCategory());
            }
            index++;
        }
    }

    public static void checkDuplicateProduct(List<ProductForm> productForms, JSONArray array) {
        Map<String,Integer> map = new HashMap<>();
        int index=1;
        for (ProductForm productForm : productForms) {
            if(map.containsKey(productForm.getBarcode()))
            {
                createProductErrorobject(productForm, array,"product barcode already exist with row number: "+index);
            }
            else{
                map.put(productForm.getBarcode(), 1);
            }
            index++;
        }
    }
    public static void checkDuplicateInventory(List<InventoryForm> inventoryForms, JSONArray array) {

    }

    public static void checkDuplicateOrderItem(List<OrderItem> orderItems, JSONArray array) {
    }

    public static void ValidateProductFormForBulkAdd(ProductForm productForm, JSONArray array, int index) {
        if (Validate.isEmpty(productForm.getBrand()) || Validate.isEmpty(productForm.getCategory()) ||
                Validate.isEmpty(productForm.getBarcode()) || Validate.isEmpty(productForm.getName())) {
            createProductErrorobject(productForm, array,"Some or All fields are empty with row number; "+index);
        }
        if(productForm.getMrp()==null || productForm.getMrp()<=0)
        {
            createProductErrorobject(productForm, array,"MRP is either Null or not correct with row number: "+index);
        }
    }


    public static void ValidateInventoryFormForBulkAdd(InventoryForm inventoryForm, JSONArray array,int index) {
        if (Validate.isEmpty(inventoryForm.getBarcode()) ) {
            createInventoryErrorobject(inventoryForm, array,"Enter barcode correctly with row number: "+index);
        }
        if(inventoryForm.getQuantity()==null || inventoryForm.getQuantity()<=0)
        {
            createInventoryErrorobject(inventoryForm, array, "Enter quantity correctly with row number: "+index);
        }
    }

    public static void ValidateOrderFormForBulkAdd(List<OrderItem> orderForm, JSONArray array) {
        for(OrderItem orderItem: orderForm)
        {
            if(isEmpty(orderItem.getBarCode()) || orderItem.getQuantity()==null || orderItem.getQuantity() < 0
                    || orderItem.getSellingPrice()==null || orderItem.getSellingPrice() < 0)
            {
                createOrderErrorobject(orderItem,array);
            }
        }
    }
}
