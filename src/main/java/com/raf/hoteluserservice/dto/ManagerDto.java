package com.raf.hoteluserservice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ManagerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate employmentDate;
    private String hotel;
    private boolean access;

    public ManagerDto() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    //    public Hotel getHotel() {
//        return hotel;
//    }
//
//    public void setHotel(Hotel hotel) {
//        this.hotel = hotel;
//    }


    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
