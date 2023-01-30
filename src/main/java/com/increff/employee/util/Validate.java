package com.increff.employee.util;

import com.increff.employee.model.Data.OrderItem;

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
			String error="Inavalid quantity of product with BarCode "+orderItem.getBarCode();
			errorMessages.add(error);
		}
		if(orderItem.getSellingPrice()<0) {
			String error="Inavalid Selling Price of product with BarCode "+orderItem.getBarCode();
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
}
