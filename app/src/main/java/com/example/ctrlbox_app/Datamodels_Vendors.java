package com.example.ctrlbox_app;

public class Datamodels_Vendors {

        private String Vendor;

        private String VendorName;

        public Datamodels_Vendors(String Vendor, String VendorName) {

            this.Vendor = Vendor;
            this.VendorName = VendorName;
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
    }



