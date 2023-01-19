package com.winterhold.dto.author;

import com.winterhold.entity.Author;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AuthorHeaderDTO {

    private Long id;
    private String title;
    private String fullName;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;

    public AuthorHeaderDTO(Long id, String title, String fullName, LocalDate birthDate, LocalDate deceasedDate, String education, String summary) {
        this.id = id;
        this.title = title;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
        this.education = education;
        this.summary = summary;
    }

    public static List<AuthorHeaderDTO> toList(List<Author> authors){
        List<AuthorHeaderDTO> result = new ArrayList<>();

        for (Author author: authors) {
            result.add(new AuthorHeaderDTO(
                    author.getId(),
                    author.getTitle(),
                    author.getFullName(),
                    author.getBirthDate(),
                    author.getDeceasedDate(),
                    author.getEducation(),
                    author.getSummary()));
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeceasedDate() {
        return deceasedDate;
    }

    public String getEducation() {
        return education;
    }

    public String getSummary() {
        return summary;
    }
}
