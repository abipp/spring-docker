package com.winterhold.dto.author;

import com.winterhold.entity.Book;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuthorBooksGridDTO {
    private final String id;
    private final String title;
    private final String categoryName;
    private final String fullName;
    private final String isBorrowed;
    private final String releaseDate;
    private final Integer totalPage;

    public AuthorBooksGridDTO(String id, String title, String categoryName, String fullName, String isBorrowed, String releaseDate, Integer totalPage) {
        this.id = id;
        this.title = title;
        this.categoryName = categoryName;
        this.fullName = fullName;
        this.isBorrowed = isBorrowed;
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }

    public static List<AuthorBooksGridDTO> convert(List<Book> content) {
        List<AuthorBooksGridDTO> result = new ArrayList<>();
        DateTimeFormatter indoFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("id-ID"));

        for (Book book : content) {
            result.add(new AuthorBooksGridDTO(
                    book.getCode(),
                    book.getTitle(),
                    book.getCategoryName(),
                    book.getAuthor().getFullName(),
                    book.getBorrowed() ? "Borrowed" : "Available",
                    book.getReleaseDate() == null ? "-" : book.getReleaseDate().format(indoFormat),
                    book.getTotalPage()
            ));
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsBorrowed() {
        return isBorrowed;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
}
