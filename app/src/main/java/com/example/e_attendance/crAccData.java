package com.example.e_attendance;

public class crAccData {
    public String firstName;
    public String lastName;
    public String address;
    public String contactNo;
    public crAccData(String firstName,String lastName,String address,String contactNo){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address =address;
        this.contactNo = contactNo;

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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
