package com.example.ctrlbox_app;

public class Datamodels {

    private int Boxid;
    private String  Vendor;
    private String VendorName;
    private String Trantype ;

    public Datamodels(int Boxid, String Vendor, String VendorName, String Trantype) {
        this.Boxid = Boxid;
        this.Vendor = Vendor;
        this.VendorName = VendorName;
        this.Trantype = Trantype;
    }


    public int getBoxid() {
        return Boxid;
    }

    public String getVendor() {
        return Vendor;
    }

    public String getVendorName() {
        return VendorName;
    }

    public String getTrantype() {
        return Trantype;
    }


}

