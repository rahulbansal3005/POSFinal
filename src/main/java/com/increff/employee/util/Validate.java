package com.increff.employee.util;

import com.increff.employee.model.Data.OrderItem;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.model.Form.ProductForm;
import com.increff.employee.service.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validate {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}

	public static void checkOrderItem(OrderItem orderItem, List<String> errorMessages)
	{
//		if(orderItem.getQuantity()==null)
//			throw new ApiException("Entered Quantity is not right");
//		if(orderItem.getSellingPrice()==null)
//			throw new ApiException("Entered Quantity is not right");
		if(orderItem.getQuantity()<0) {
			String error="Invalid quantity of product with BarCode "+orderItem.getBarCode();
			errorMessages.add(error);
		}
		if(orderItem.getSellingPrice()<0) {
			String error="Invalid Selling Price of product with BarCode "+orderItem.getBarCode();
			errorMessages.add(error);
		}
	}

	public static void ContainDuplicates(OrderItem[] orderForm, List<String> errorMessages) {
		Map<String,Integer> map=new HashMap<>();
		for(OrderItem orderItem:orderForm)
		{
			if(map.containsKey(orderItem.getBarCode()))
			{
				String error="Duplicate product with BarCode "+orderItem.getBarCode();
				errorMessages.add(error);
			}
			else{
				map.put(orderItem.getBarCode(),1);
			}
		}
	}

    public static void BrandForm (BrandForm brandForm) throws ApiException {
		if (Validate.isEmpty(brandForm.getBrand())) {
			throw new ApiException("brand name cannot be null or empty");
		}
		if (Validate.isEmpty(brandForm.getCategory())) {
			throw new ApiException("category name cannot be null or empty");
		}
    }

	public static void validateInventoryFormonAdd(InventoryForm inventoryForm) throws ApiException {
		if(isEmpty(inventoryForm.getBarcode()))
			throw new ApiException("BarCode is Null or Empty");
		if(inventoryForm.getQuantity()==null)
			throw new ApiException("Entered Quantity is NULL");
		if(inventoryForm.getQuantity()<0)
			throw new ApiException("Entered Quantity is negative");
	}
	public static void validateInventoryFormonUpdate(InventoryForm inventoryForm) throws ApiException {
		System.out.println(inventoryForm.getBarcode());
		if(inventoryForm.getQuantity()==null)
			throw new ApiException("Entered Quantity is NULL");
		if(inventoryForm.getQuantity()<0)
			throw new ApiException("Entered Quantity is negative");
	}
	public static void validateProductFormOnUpdate(ProductForm productForm) throws ApiException {
//		if(productForm.getName()=="" || productForm.getName()==null)
		if(isEmpty(productForm.getName()))
			throw new ApiException("Name Field is Empty or Null");
		if(productForm.getMrp()==null)
			throw new ApiException("MRP is not entered");
		if(productForm.getMrp()<0)
			throw new ApiException("MRP entered is negative");
	}
	public static void validateproductFormonAdd(ProductForm productForm) throws ApiException {
		if (Validate.isEmpty(productForm.getName())) {
			throw new ApiException("Name cannot be empty or null");
		}if (Validate.isEmpty(productForm.getBarcode())) {
			throw new ApiException("Barcode cannot be empty or null");
		}if (Validate.isEmpty(productForm.getBrand())) {
			throw new ApiException("Brand cannot be empty or null");
		}if (Validate.isEmpty(productForm.getCategory())) {
			throw new ApiException("Category cannot be null or empty");
		}if (productForm.getMrp()==null) {
			throw new ApiException("MRP cannot be empty");
		}
		if (productForm.getMrp()<0) {
			throw new ApiException("MRP cannot be negative");
		}

	}
}
