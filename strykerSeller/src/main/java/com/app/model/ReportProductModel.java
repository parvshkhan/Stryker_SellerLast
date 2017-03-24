package com.app.model;

/**
 * Created by Inflectica on 10/28/2015.
 */
public class ReportProductModel {

    String proName;
    String totalQuantity;
    String totalPrice;
    String proImage;
    String userName;
    String orderDate;

    public String getOrderDate() {

        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ReportProductModel(String userName, String totalPrice, String totalQuantity) {
        this.userName = userName;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public ReportProductModel( String categoryName, String proName,String totalPrice, String totalQuantity) {
        this.proName = proName;
        this.categoryName = categoryName;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    String categoryName;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getTotalQuantity() {

        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
