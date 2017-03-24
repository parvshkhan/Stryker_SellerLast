package com.app.model;

public class StoreHomeListModel {
	
	String srore_id, store_image, store_name;

	public StoreHomeListModel(String srore_id, String store_image,
			String store_name) {
		super();
		this.srore_id = srore_id;
		this.store_image = store_image;
		this.store_name = store_name;
	}

	public StoreHomeListModel() {

	}

	public String getSrore_id() {
		return srore_id;
	}

	public void setSrore_id(String srore_id) {
		this.srore_id = srore_id;
	}

	public String getStore_image() {
		return store_image;
	}

	public void setStore_image(String store_image) {
		this.store_image = store_image;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	} 
}
