package com.app.model;

/**
 * Created by Inflectica on 10/26/2015.
 */
public class ChatUserModel {

    String userId, userImage, userName, description, date,time, buyerChatId;

    public String getBuyerChatId() {
		return buyerChatId;
	}

	public void setBuyerChatId(String buyerChatId) {
		this.buyerChatId = buyerChatId;
	}

	public ChatUserModel(String userId, String userImage, String userName, String buyerChatId) {
        this.userId = userId;
        this.userImage = userImage;
        this.userName = userName;
        this.buyerChatId = buyerChatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
