package com.example.contact_amandeep_790018_android.model;

public class
Contact {

    int id;
    String fname, lname, email, address;
    double phno;

    public Contact(int id, String fname, String lname, String email, double phno, String address) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phno = phno;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public double getPhno() {
        return phno;
    }

    public String getAddress() {
        return address;
    }

}
