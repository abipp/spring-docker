package com.winterhold.dto.customer;

import com.winterhold.entity.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerGridDTO {

    private String id;

    private String firstName;

    private String lastName;
    private String fullName;

    private LocalDate birthDate;

    private String gender;
    private String phone;
    private String address;
    private LocalDate expiredDate;

    public static List<CustomerGridDTO> toList(List<Customer> customers){
        List<CustomerGridDTO> result = new ArrayList<>();

        for (Customer customer: customers) {
            result.add(new CustomerGridDTO(
                    customer.getMembershipNumber(),
                    customer.getFullName(),
                    customer.getMembershipExpiredDate().plusYears(2)));
        }
        return result;
    }

    public CustomerGridDTO(){}

    public CustomerGridDTO(String id, String fullName, LocalDate expiredDate) {
        this.id = id;
        this.fullName = fullName;
        this.expiredDate = expiredDate;
    }

    public CustomerGridDTO(String id, String firstName, String lastName,
                           String fullName, LocalDate birthDate, String gender,
                           String phone, String address, LocalDate expiredDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.expiredDate = expiredDate;
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

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
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
}
