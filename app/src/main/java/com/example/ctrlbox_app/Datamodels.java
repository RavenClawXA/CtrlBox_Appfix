package com.example.ctrlbox_app;

public class Datamodels {
    private String BoxId;
    private String Vendor;
    private  String VendorName;
    private  String TransDate;
    private String TransType ;

    public Datamodels(String BoxId, String Vendor, String VendorName, String TransDate, String TransType) {

        //String object =  "{\"BoxId\":\"1222\",\"Vendor\",\"VendorName\",\"Trandate\"}";
        this.BoxId = BoxId;
        this.Vendor = Vendor;
        this.VendorName = VendorName;
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

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
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

