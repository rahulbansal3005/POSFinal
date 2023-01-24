package com.increff.employee.model;

public class OrderData extends OrderForm {
    private int id;
    // private int orderId;
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // public int getOrderId() {
    // return orderId;
    // }

    // public void setOrderId(int orderId) {
    // this.orderId = orderId;
    // }
}
