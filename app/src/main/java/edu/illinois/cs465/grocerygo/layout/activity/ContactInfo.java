package edu.illinois.cs465.grocerygo.layout.activity;

public class ContactInfo {
    String contactName;
    int contactImage;


    public ContactInfo(String contactName, int contactImage) {
        this.contactName = contactName;
        this.contactImage = contactImage;
    }


    public String getContactName() {
        return contactName;
    }

    public int getContactImage() {
        return contactImage;
    }
}
