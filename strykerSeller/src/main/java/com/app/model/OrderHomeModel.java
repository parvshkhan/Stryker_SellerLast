package com.app.model;

import org.json.JSONArray;

public class OrderHomeModel {
	String orderId;
	String store_id;
	String storeName;
	String orderDate;
	String expectedDate;
	String expectedTime;
	String totalPrice;
	String categoryName;
	String userName;
	String userImage;
	String orderTime;
	String userDetails;
	
	String dateTimeValue ;

	public String getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateTimeValue(String dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	public String getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}

	public OrderHomeModel(String store_id, String orderId, String userName, String userId, String orderDate) {
		this.store_id = store_id;
		this.orderId = orderId;
		this.userName = userName;
		this.userId = userId;
		this.orderDate = orderDate;
	}

	public OrderHomeModel(String store_id, String orderId, String userId, String userName, String userImage, String orderTime, JSONArray categoryJosnArray, String userDetails, String dateTimeValue) {
		this.store_id = store_id;
		this.orderId = orderId;
		this.userId = userId;
		this.userName = userName;
		this.categoryJosnArray = categoryJosnArray;
		this.userImage = userImage;
		this.orderTime = orderTime;
		this.userDetails = userDetails;
		this.dateTimeValue = dateTimeValue;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	String userId;
	JSONArray categoryJosnArray;
	public OrderHomeModel(String orderId, String store_id, String storeName,
			String orderTime, String orderDate, String expectedDate,
			String expectedTime, String totalPrice, String categoryName,
			JSONArray categoryJosnArray) {
		super();
		this.orderId = orderId;
		this.store_id = store_id;
		this.storeName = storeName;
		this.orderTime = orderTime;
		this.orderDate = orderDate;
		this.expectedDate = expectedDate;
		this.expectedTime = expectedTime;
		this.totalPrice = totalPrice;
		this.categoryName = categoryName;
		this.categoryJosnArray = categoryJosnArray;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public String getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public JSONArray getCategoryJosnArray() {
		return categoryJosnArray;
	}
	public void setCategoryJosnArray(JSONArray categoryJosnArray) {
		this.categoryJosnArray = categoryJosnArray;
	}
	
}
