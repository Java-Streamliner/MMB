package com.thisisstreamliner.mmb.models;

public class User {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String imageUrl;
    private double remainedAmount;

    public User(int id, String email, String password, String firstName, String lastName, String address, String imageUrl, double remainedAmount) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.imageUrl = imageUrl;
        this.remainedAmount = remainedAmount;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRemainedAmount() {
        return remainedAmount;
    }

    public void setRemainedAmount(double remainedAmount) {
        this.remainedAmount = remainedAmount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", remainedAmount=" + remainedAmount +
                '}';
    }
}
