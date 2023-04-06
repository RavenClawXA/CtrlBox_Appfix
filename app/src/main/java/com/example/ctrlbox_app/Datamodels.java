package com.example.ctrlbox_app;

public class Datamodels {


    private String BoxId;
    private  String  Vendor;
    private  String VendorName;
    private  String Trandate;
    private Boolean Trantype ;

    public Datamodels(String BoxId, String Vendor, String VendorName, String Trandate/*boolean Trantype*/) {

        //String object =  "{\"BoxId\":\"1222\",\"Vendor\",\"VendorName\",\"Trandate\"}";
        this.BoxId = BoxId;
        this.Vendor = Vendor;
        this.VendorName = VendorName;
        this.Trantype = Boolean.valueOf(Trantype);
        this.Trandate = Trandate;
    }


    public String getTranDate() {
        return Trandate;
    }

    public String getBoxId() {
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

