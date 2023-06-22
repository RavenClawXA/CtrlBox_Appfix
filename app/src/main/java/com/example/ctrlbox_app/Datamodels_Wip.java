package com.example.ctrlbox_app;

public class Datamodels_Wip {
    private String Job;
    private String Item;
    private String Quantity;
    private String RecipientName;
    //private String Image;

    public Datamodels_Wip(String Job, String Item, String Quantity, String RecipientName){
        this.Job = Job;
        this.Item = Item;
        this.Quantity = Quantity;
        this.RecipientName = RecipientName;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setRecipientName(String recipientName) {
        RecipientName = recipientName;
    }
}
