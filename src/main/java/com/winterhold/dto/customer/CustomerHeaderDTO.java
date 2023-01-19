package com.winterhold.dto.customer;

import java.time.LocalDate;

public class CustomerHeaderDTO {
    private String id;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
    private LocalDate membershipExpireDate;

    public CustomerHeaderDTO(){}

    public CustomerHeaderDTO(String id, String fullName, LocalDate membershipExpireDate) {
        this.id = id;
        this.fullName = fullName;
        this.membershipExpireDate = membershipExpireDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getMembershipExpireDate() {
        return membershipExpireDate;
    }

    public void setMembershipExpireDate(LocalDate membershipExpireDate) {
        this.membershipExpireDate = membershipExpireDate;
    }
}
