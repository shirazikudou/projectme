package com.example.shirazikudou.carrentproject;

import android.widget.ImageButton;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDetails {
    public String image;
    public String fullName;
    public String email;
    public String password;
    public String matricNumber;
    public String inasis;
    public String course;

    public UserDetails(){

    }

    public UserDetails(String image, String fullName, String email, String password, String matricNumber, String inasis, String course) {
        this.image = image;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.matricNumber = matricNumber;
        this.inasis = inasis;
        this.course = course;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getInasis() {
        return inasis;
    }

    public void setInasis(String inasis) {
        this.inasis = inasis;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "image='" + image + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", matricNumber='" + matricNumber + '\'' +
                ", inasis='" + inasis + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
