package com.increff.employee.model;


public class PdfListData {

	private Integer sno;

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	private Integer quantity;
	private String barcode;
	private String product;
	private Double unitPrice;

	private Double amount;
}
