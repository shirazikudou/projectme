package com.example.shirazikudou.carrentproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Customer {
    public String cName;
    public String cContact;
    public String cUsetime;
    public String cBookingtime;
    public String cLicence;

    public Customer(){}

    public Customer(String cName, String cContact, String cUsetime, String cBookingtime, String cLicence) {
        this.cName = cName;
        this.cContact = cContact;
        this.cUsetime = cUsetime;
        this.cBookingtime = cBookingtime;
        this.cLicence = cLicence;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcContact() {
        return cContact;
    }

    public void setcContact(String cContact) {
        this.cContact = cContact;
    }

    public String getcUsetime() {
        return cUsetime;
    }

    public void setcUsetime(String cUsetime) {
        this.cUsetime = cUsetime;
    }

    public String getcBookingtime() {
        return cBookingtime;
    }

    public void setcBookingtime(String cBookingtime) {
        this.cBookingtime = cBookingtime;
    }

    public String getcLicence() {
        return cLicence;
    }

    public void setcLicence(String cLicence) {
        this.cLicence = cLicence;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cName='" + cName + '\'' +
                ", cContact='" + cContact + '\'' +
                ", cUsetime='" + cUsetime + '\'' +
                ", cBookingtime='" + cBookingtime + '\'' +
                ", cLicence='" + cLicence + '\'' +
                '}';
    }
}
