package com.winterhold.dto.author;

import com.winterhold.validation.After;
import com.winterhold.validation.FutureParadox;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@After(message = "tanggal lahir harus kurang dari tanggal wafat", previousDateField = "birthDate", subsequentDateField = "deceasedDate")
public class UpsertAuthorDTO {

    private Long id;

    @NotBlank(message = "Title harus diisi")
    private String title;

    @NotBlank(message = "First Name harus diisi")
    private String firstName;

    private String lastName;

    @NotNull(message = "BirthDate harus diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureParadox(message = "Tanggal lahir tidak boleh lebih dari tanggal sekarang")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;

    private String education;

    private String summary;

    public UpsertAuthorDTO(){

    }

    public UpsertAuthorDTO(Long id, String title, String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education, String summary) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
        this.education = education;
        this.summary = summary;
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

    public LocalDate getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(LocalDate deceasedDate) {
        this.deceasedDate = deceasedDate;
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
