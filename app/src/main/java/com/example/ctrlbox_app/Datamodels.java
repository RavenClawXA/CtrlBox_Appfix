package com.example.ctrlbox_app;

public class Datamodels {


    final private int BoxId;
    private  String  Vendor;
    private  String VendorName;
    private Boolean Trantype ;

    public Datamodels(String BoxId, String Vendor, String VendorName /*boolean Trantype*/) {

        this.BoxId = Integer.parseInt(BoxId);
        this.Vendor = Vendor;
        this.VendorName = VendorName;
        this.Trantype = Boolean.valueOf(Trantype);
    }

    public int getBoxId() {
        return BoxId;
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

    public Boolean getTrantype() {
        return Trantype;
    }

    public void setTrantype(Boolean trantype) {
        Trantype = trantype;
    }
}

