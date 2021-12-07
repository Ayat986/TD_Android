package com.example.tp5;

public class Contact {

    private String  firstName;
    private String  lastName;
    private String  imageUrl;

    public Contact(String firstName, String lastName, String imageUrl){
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;

    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
    return firstName;
    }

    public String getLastName() {
    return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
