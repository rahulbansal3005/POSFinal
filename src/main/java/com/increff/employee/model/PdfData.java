package com.increff.employee.model;

import java.util.List;

public class PdfData {
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public List<PdfListData> getItemList() {
		return itemList;
	}

	public void setItemList(List<PdfListData> itemList) {
		this.itemList = itemList;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	private Integer orderId;
	private String invoiceDate;
	private String invoiceTime;
	private List<PdfListData> itemList;

	private Double total;

}
