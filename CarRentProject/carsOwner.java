package com.example.shirazikudou.carrentproject;

public class CarsOwner {
    public String image;
    public String name;
    public String merk;
    public String platNumber;
    public String contact;

    public CarsOwner(){

    }

    public CarsOwner(String image, String name, String merk, String platNumber, String contact){
        this.image = image;
        this.name   = name;
        this.merk = merk;
        this.platNumber = platNumber;
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "CarsOwner{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", merk='" + merk + '\'' +
                ", platNumber='" + platNumber + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
