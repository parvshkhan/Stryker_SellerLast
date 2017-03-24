package com.app.model;

/**
 * Created by Inflac on 16-10-2015.
 */
public class ModelSellerproductList {

    String productname ,product_price, product_desc;

    public ModelSellerproductList(String productname, String product_price, String product_desc) {
        this.productname = productname;
        this.product_price = product_price;
        this.product_desc = product_desc;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }
}
