package com.app.model;

/**
 * Created by Inflectica on 10/28/2015.
 */
public class OrderHistoryModel {

    String orderDate,totalUnit,totalPrice,orderId;

    public OrderHistoryModel(String orderDate, String totalUnit, String totalPrice, String orderId) {
        this.orderDate = orderDate;
        this.totalUnit = totalUnit;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }

    public OrderHistoryModel(String orderDate, String totalPrice, String totalUnit) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.totalUnit = totalUnit;
    }

    public OrderHistoryModel() {

    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(String totalUnit) {
        this.totalUnit = totalUnit;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
