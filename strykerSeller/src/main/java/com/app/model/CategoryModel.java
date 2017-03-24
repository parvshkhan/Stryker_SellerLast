package com.app.model;

/**
 * Created by Inflectica on 11/4/2015.
 */
public class CategoryModel {

    String catId, catName;

    public CategoryModel() {

    }

    public CategoryModel(String catName) {
        this.catName = catName;
    }

    public CategoryModel(String catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
