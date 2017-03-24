package com.app.model;

/**
 * Created by Inflac on 19-10-2015.
 */
public class SellerProductListModel {

    String productId, productname, product_desc, product_img_Url,categoryname;
    String price;
    Boolean isShowHeader, isLastItem;

    public SellerProductListModel(String productId, String productname, String product_desc, String product_img_Url,
                                  String categoryname, String price, Boolean isShowHeader, Boolean isLastItem) {
        this.productId = productId;
        this.productname = productname;
        this.product_desc = product_desc;
        this.product_img_Url = product_img_Url;
        this.categoryname = categoryname;
        this.price = price;
        this.isShowHeader = isShowHeader;
        this.isLastItem = isLastItem;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_img_Url() {
        return product_img_Url;
    }

    public void setProduct_img_Url(String product_img_Url) {
        this.product_img_Url = product_img_Url;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
}
