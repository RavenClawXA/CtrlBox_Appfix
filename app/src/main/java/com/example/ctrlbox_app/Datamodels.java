package com.example.ctrlbox_app;

public class Datamodels {
    private String BoxId;
    private String Vendor;
    private  String GetFrom;
    private String SendTo;
    private  String TransDate;
    private String TransType ;

    public Datamodels(String BoxId, String Vendor) {

        //String object =  "{\"BoxId\":\"1222\",\"Vendor\",\"VendorName\",\"Trandate\"}";
        this.BoxId = BoxId;
        this.Vendor = Vendor;
        this.GetFrom = GetFrom;
        this.SendTo = SendTo;
        this.TransDate = TransDate;
        this.TransType = TransType;
    }

    public String getBoxId() {
        return BoxId;
    }

    public void setBoxId(String boxId) {
        BoxId = boxId;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getGetFrom() {
        return GetFrom;
    }

    public void setGetFrom(String getFrom) {
        GetFrom = getFrom;
    }

    public String getSendTo() {
        return SendTo;
    }

    public void setSendTo(String sendTo) {
        SendTo = sendTo;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }
}

