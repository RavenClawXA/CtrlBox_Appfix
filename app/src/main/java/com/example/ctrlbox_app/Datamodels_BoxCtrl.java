package com.example.ctrlbox_app;

public class Datamodels_BoxCtrl {
    private String BoxId;
    private String BoxName;
    private String Vendor;

    public Datamodels_BoxCtrl(String BoxId, String BoxName, String Vendor) {
        this.BoxId = BoxId;
        this.BoxName = BoxName;
        this.Vendor = Vendor;
    }

    public String getBoxId() {
        return BoxId;
    }

    public void setBoxId(String boxId) {
        BoxId = boxId;
    }

    public String getBoxName() {
        return BoxName;
    }

    public void setBoxName(String boxName) {
        BoxName = boxName;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }
}
