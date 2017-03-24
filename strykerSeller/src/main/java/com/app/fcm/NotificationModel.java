package com.app.fcm;

/**
 * Created by lenvo on 29/11/2016.
 */
public class NotificationModel {
    private String IsVStatus;
    private String MsgID;
    private String Vstatus;
    private String RecieverID;
    private String RecieverName;
    private String Heading;
    private String Message;
    private String VoucherID;
    private String Vremark;
    private String Vstatusby;
    private String statusupdatedate;

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getIsVStatus() {
        return IsVStatus;
    }

    public void setIsVStatus(String isVStatus) {
        IsVStatus = isVStatus;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getVstatus() {
        return Vstatus;
    }

    public void setVstatus(String vstatus) {
        Vstatus = vstatus;
    }

    public String getRecieverID() {
        return RecieverID;
    }

    public void setRecieverID(String recieverID) {
        RecieverID = recieverID;
    }

    public String getRecieverName() {
        return RecieverName;
    }

    public void setRecieverName(String recieverName) {
        RecieverName = recieverName;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private String MessageType;

    public String getVoucherID() {
        return VoucherID;
    }

    public void setVoucherID(String voucherID) {
        VoucherID = voucherID;
    }

    public String getVremark() {
        return Vremark;
    }

    public void setVremark(String vremark) {
        Vremark = vremark;
    }

    public String getVstatusby() {
        return Vstatusby;
    }

    public void setVstatusby(String vstatusby) {
        Vstatusby = vstatusby;
    }

    public String getStatusupdatedate() {
        return statusupdatedate;
    }

    public void setStatusupdatedate(String statusupdatedate) {
        this.statusupdatedate = statusupdatedate;
    }

    // "MsgID":"20161130163006955","SenderName":"QCIAdmin","SenderID":"180018004545","RefID":"","Reply":"N",
   // "Heading":"Testing","Message":"Hi This is Testing Msg.","MessageType":"T","FilePath":"","Validity":"-1",
   // "SenderIcon":"","VoucherID":"0","IsVStatus":"N","Vstatus":"Approved","RecieverID":"NABH","RecieverName":"Rakesh"
   }
