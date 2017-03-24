package com.app.model;

import java.sql.Time;

/**
 * Created by Inflectica on 10/15/2015.
 */
public class StoreModel {

    public StoreModel(String storeId, String busName, String busAddress, String busPhone, String busEmail, Time startTime, Time endTime, Time deliveryTime, Integer distance, Boolean isOpenOnMonday, Boolean isOpenOnTuesday, Boolean isOpenOnWednesday, Boolean isOpenOnThursday, Boolean isOpenOnFriday, Boolean isOpenOnSaturday, Boolean isOpenOnSunday, Boolean isDeliveryAvailable, String eBrochure) {
        this.storeId = storeId;
        this.busName = busName;
        this.busAddress = busAddress;
        this.busPhone = busPhone;
        this.busEmail = busEmail;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deliveryTime = deliveryTime;
        this.distance = distance;
        this.isOpenOnMonday = isOpenOnMonday;
        this.isOpenOnTuesday = isOpenOnTuesday;
        this.isOpenOnWednesday = isOpenOnWednesday;
        this.isOpenOnThursday = isOpenOnThursday;
        this.isOpenOnFriday = isOpenOnFriday;
        this.isOpenOnSaturday = isOpenOnSaturday;
        this.isOpenOnSunday = isOpenOnSunday;
        this.isDeliveryAvailable = isDeliveryAvailable;
        this.eBrochure = eBrochure;
    }

    public StoreModel(Integer sellerId, String storeId, String busName, String openTime, String closeTime, String lat, String lon) {
        this.sellerId = sellerId;
        this.storeId = storeId;
        this.busName = busName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.lat = lat;
        this.lon = lon;
    }

    public StoreModel(String storeId, String busName){

        this.storeId = storeId;
        this.busName = busName;
    }

    private String storeId, busName, busAddress, busPhone, busEmail, openTime,closeTime, lat, lon ;
    private Time startTime, endTime, deliveryTime;
    private Integer distance, sellerId ;
    private Boolean isOpenOnMonday, isOpenOnTuesday, isOpenOnWednesday, isOpenOnThursday, isOpenOnFriday, isOpenOnSaturday, isOpenOnSunday;
    private Boolean isDeliveryAvailable;
    private String eBrochure;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }



    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusAddress() {
        return busAddress;
    }

    public void setBusAddress(String busAddress) {
        this.busAddress = busAddress;
    }

    public String getBusPhone() {
        return busPhone;
    }

    public void setBusPhone(String busPhone) {
        this.busPhone = busPhone;
    }

    public String getBusEmail() {
        return busEmail;
    }

    public void setBusEmail(String busEmail) {
        this.busEmail = busEmail;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Time deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Boolean getIsOpenOnMonday() {
        return isOpenOnMonday;
    }

    public void setIsOpenOnMonday(Boolean isOpenOnMonday) {
        this.isOpenOnMonday = isOpenOnMonday;
    }

    public Boolean getIsOpenOnTuesday() {
        return isOpenOnTuesday;
    }

    public void setIsOpenOnTuesday(Boolean isOpenOnTuesday) {
        this.isOpenOnTuesday = isOpenOnTuesday;
    }

    public Boolean getIsOpenOnWednesday() {
        return isOpenOnWednesday;
    }

    public void setIsOpenOnWednesday(Boolean isOpenOnWednesday) {
        this.isOpenOnWednesday = isOpenOnWednesday;
    }

    public Boolean getIsOpenOnThursday() {
        return isOpenOnThursday;
    }

    public void setIsOpenOnThursday(Boolean isOpenOnThursday) {
        this.isOpenOnThursday = isOpenOnThursday;
    }

    public Boolean getIsOpenOnFriday() {
        return isOpenOnFriday;
    }

    public void setIsOpenOnFriday(Boolean isOpenOnFriday) {
        this.isOpenOnFriday = isOpenOnFriday;
    }

    public Boolean getIsOpenOnSaturday() {
        return isOpenOnSaturday;
    }

    public void setIsOpenOnSaturday(Boolean isOpenOnSaturday) {
        this.isOpenOnSaturday = isOpenOnSaturday;
    }

    public Boolean getIsOpenOnSunday() {
        return isOpenOnSunday;
    }

    public void setIsOpenOnSunday(Boolean isOpenOnSunday) {
        this.isOpenOnSunday = isOpenOnSunday;
    }

    public Boolean getIsDeliveryAvailable() {
        return isDeliveryAvailable;
    }

    public void setIsDeliveryAvailable(Boolean isDeliveryAvailable) {
        this.isDeliveryAvailable = isDeliveryAvailable;
    }

    public String geteBrochure() {
        return eBrochure;
    }

    public void seteBrochure(String eBrochure) {
        this.eBrochure = eBrochure;
    }

    
    public String toString() {
        return this.getStoreId().toString();
    }

}
