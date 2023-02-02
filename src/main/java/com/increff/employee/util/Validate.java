package com.increff.employee.util;

import com.increff.employee.model.Data.OrderItem;
import com.increff.employee.model.Form.BrandForm;
import com.increff.employee.model.Form.InventoryForm;
import com.increff.employee.service.ApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validate {

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	// public static boolean isEmpty(int s) {
	// return s == null;
	// }

	public static String toLowerCase(String s) {
		return s == null ? null : s.trim().toLowerCase();
	}

	public static void checkOrderItem(OrderItem orderItem, List<String> errorMessages)
	{
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

    public static void checkBrandCategory(BrandForm brandForm) {
    }

	public static void validateInventoryForm(InventoryForm inventoryForm) throws ApiException {
		if(inventoryForm.getQuantity()<0)
			throw new ApiException("Entered Quantity is negative");
		if(inventoryForm.getQuantity()==null)
			throw new ApiException("Entered Quantity is not right");
		if(inventoryForm.getBarcode().equals(""))
			throw new ApiException("Bar Code field is empty");
		if(inventoryForm.getBarcode()==null)
			throw new ApiException("Bar Code field is NULL");

	}
}
