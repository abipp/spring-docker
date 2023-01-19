package com.winterhold.dto.author;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuthorGridDTO {
    private Long id;
    private String title;
    private String fullName;
    private long age;
    private String birthDate;
    private String status;
    private String education;
    private String summary;

    public AuthorGridDTO(Long id, String title, String fullName, long age, String birthDate, String status, String education) {
        this.id = id;
        this.title = title;
        this.fullName = fullName;
        this.age = age;
        this.birthDate = birthDate;
        this.status = status;
        this.education = education;
    }

    public AuthorGridDTO(Long id, String fullName, String birthDate, String status ,String education, String summary) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.status = status;
        this.education = education;
        this.summary = summary;
    }

    public static List<AuthorGridDTO> convert(List<AuthorHeaderDTO> authorDtos) {
        DateTimeFormatter indoFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("id-ID"));
        List<AuthorGridDTO> result = new ArrayList<>();

        for (AuthorHeaderDTO authorDto : authorDtos) {
            long age;
            if (authorDto.getDeceasedDate() == null) {
                age  = ChronoUnit.YEARS.between(authorDto.getBirthDate(), LocalDate.now());
            } else{
                age  = ChronoUnit.YEARS.between(authorDto.getBirthDate(), authorDto.getDeceasedDate());
            }
            result.add(new AuthorGridDTO(
                    authorDto.getId(),
                    authorDto.getTitle(),
                    authorDto.getFullName(),
                    age,
                    authorDto.getBirthDate().format(indoFormat),
                    authorDto.getDeceasedDate() == null ? "Alive" : "Deceased",
                    authorDto.getEducation()));
        }
        return result;
    }

    public static AuthorGridDTO convertBook(UpsertAuthorDTO author) {
        DateTimeFormatter indoFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("id-ID"));
        return new AuthorGridDTO(
                author.getId(),
                author.getTitle() + ". " + author.getFirstName() + " " + author.getLastName(),
                author.getBirthDate().format(indoFormat),
                author.getDeceasedDate() == null ? "-" : author.getDeceasedDate().format(indoFormat),
                author.getEducation(),
                author.getSummary());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
