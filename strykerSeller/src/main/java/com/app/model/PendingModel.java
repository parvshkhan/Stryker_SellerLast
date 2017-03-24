package com.app.model;

/**
 * Created by Inflectica on 10/27/2015.
 */
public class PendingModel {

    String ProName;
    String ProDescription;
    String Quantity;
    String TotalCost;
    String storeName;
    String proCategory;
    String productId;
    String isHot;
    
    
    String productImageUrl;

    public PendingModel(String productId, String prodImageUrl, String proPrice, String isHot, String proDesc, String proName) {

    }

    public PendingModel() {

    }

    public PendingModel(String productId, String prodImageUrl, String proPrice, String isHot, String proDesc, String proName, boolean b) {

    }


    public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	Boolean isShowHeader, isLastItem;

    public PendingModel(String proCategory,String proId, String proName, String proDescription, String totalCost,String is_hot, Boolean isShowHeader, String productImageUrl) {
    	
        this.proCategory = proCategory;
        ProName = proName;
        productId = proId;
        ProDescription = proDescription;
        TotalCost = totalCost;
        isHot = is_hot;
        this.isShowHeader = isShowHeader;
        this.productImageUrl = productImageUrl;
    }

    public PendingModel(String proCategory, String proName, String proDescription, String totalCost, Boolean isShowHeader, String productImageUrl) {
        this.proCategory = proCategory;
        ProName = proName;
        ProDescription = proDescription;
        TotalCost = totalCost;
        this.isShowHeader = isShowHeader;
        this.productImageUrl = productImageUrl;
    }

    public PendingModel(String proName, String proDescription, String quantity, String totalCost,String productImageUrl) {
        ProName = proName;
        ProDescription = proDescription;
        Quantity = quantity;
        TotalCost = totalCost;
        this.productImageUrl = productImageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProCategory() {
        return proCategory;
    }

    public void setProCategory(String proCategory) {
        this.proCategory = proCategory;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getProDescription() {
        return ProDescription;
    }

    public void setProDescription(String proDescription) {
        ProDescription = proDescription;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(String totalCost) {
        TotalCost = totalCost;
    }

    public Boolean getIsShowHeader() {
        return isShowHeader;
    }

    public void setIsShowHeader(Boolean isShowHeader) {
        this.isShowHeader = isShowHeader;
    }

    public Boolean getIsLastItem() {
        return isLastItem;
    }

    public void setIsLastItem(Boolean isLastItem) {
        this.isLastItem = isLastItem;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }
}
