package com.winterhold.dto.loan;

import java.time.LocalDate;

public class LoanDetailDTO {
    //customer
    private Long id;
    private String customerNumber;
    private String customerFullName;
    private String phone;
    private LocalDate expiredDate;
    //book
    private String title;
    private String categoryName;
    private String authorFullName;
    private Integer floor;
    private String isle;
    private Integer bay;

    public LoanDetailDTO(){}

    public LoanDetailDTO(Long id, String customerNumber, String customerFullName,
                         String phone, LocalDate expiredDate, String title,
                         String categoryName, String authorFullName, Integer floor,
                         String isle, Integer bay) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.customerFullName = customerFullName;
        this.phone = phone;
        this.expiredDate = expiredDate;
        this.title = title;
        this.categoryName = categoryName;
        this.authorFullName = authorFullName;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getIsle() {
        return isle;
    }

    public void setIsle(String isle) {
        this.isle = isle;
    }

    public Integer getBay() {
        return bay;
    }

    public void setBay(Integer bay) {
        this.bay = bay;
    }
}
