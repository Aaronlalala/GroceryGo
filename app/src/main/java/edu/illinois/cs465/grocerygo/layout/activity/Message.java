package edu.illinois.cs465.grocerygo.layout.activity;

public class Message {
    private String message;
    private int contact_image;

    public Message(String message, int contact_image) {
        this.message = message;
        this.contact_image = contact_image;
    }

    public String getMessage() {
        return message;
    }

    public int getContact_image() {
        return contact_image;
    }
}
