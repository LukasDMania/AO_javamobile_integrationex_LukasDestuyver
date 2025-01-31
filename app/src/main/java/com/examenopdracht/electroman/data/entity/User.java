package com.examenopdracht.electroman.data.entity;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private LocalDate birthDate;
    private String municipality;
    private String postalCode;
    private String street;
    private String houseNumber;
    private String box;

    // Constructors
    public User() {
    }
    @Ignore
    public User(String firstName, String lastName, String userName, String password, LocalDate birthDate, String municipality, String postalCode, String street, String houseNumber, String box) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.municipality = municipality;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.box = box;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getBox() {
        return box;
    }
    public void setBox(String box) {
        this.box = box;
    }
}
