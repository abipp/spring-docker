package com.winterhold.dto.book;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BookGridDTO {

    private String id;
    private String title;
    private Boolean isBorrowed;

    private String fullName;

    private String borrowed;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Integer totalPage;

    public BookGridDTO() {
    }

    public BookGridDTO(String id, String title, Boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.isBorrowed = isBorrowed;
    }

    public BookGridDTO(String id, String title, Boolean isBorrowed, String fullName, LocalDate releaseDate, Integer totalPage) {
        this.id = id;
        this.title = title;
        this.isBorrowed = isBorrowed;
        this.fullName = fullName;
        if (isBorrowed){
            this.borrowed = "Borrowed";
        }else {
            this.borrowed = "Available";
        }
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        isBorrowed = borrowed;
    }
}
