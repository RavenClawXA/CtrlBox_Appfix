package com.example.ctrlbox_app;

public class Datamodels {

    private int BoxId;
    private String  Vendor;
    private String VendorName;
    private Boolean Trantype ;

    public Datamodels(String BoxId, String Vendor, String VendorName/*boolean Trantype*/) {
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

    public String getVendorName() {
        return VendorName;
    }

    public boolean getTrantype() {
        return Trantype;
    }

    public void setBoxId(int BoxId) {
        BoxId = BoxId;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public void setTrantype(Boolean trantype) {
        Trantype = trantype;
    }
}

