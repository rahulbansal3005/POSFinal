package com.increff.employee.util;

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

}
