package com.winterhold.dto.book;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UpsertBookDTO {
    private  String code;
    private  String title;
    private  String categoryName;
    private  Long author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private  Integer totalPage;
    private  String summary;

    public static UpsertBookDTO from(UpsertBookDTO booksGridDto,
                                                  String categoryName){
        return new UpsertBookDTO(
                booksGridDto.getCode(),
                booksGridDto.getTitle(),
                categoryName,
                booksGridDto.getAuthor(),
                booksGridDto.getReleaseDate() == null ? null : booksGridDto.getReleaseDate(),
                booksGridDto.getTotalPage(),
                booksGridDto.getSummary());
    }

    public UpsertBookDTO() {
    }

    public UpsertBookDTO(String code, String title, String categoryName, Long author, LocalDate releaseDate, Integer totalPage, String summary) {
        this.code = code;
        this.title = title;
        this.categoryName = categoryName;
        this.author = author;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
        this.summary = summary;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
