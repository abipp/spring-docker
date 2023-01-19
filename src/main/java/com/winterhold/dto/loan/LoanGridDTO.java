package com.winterhold.dto.loan;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanGridDTO {

    private Long id;
    private String title;
    private String fullName;
    private String loanDate;
    private String dueDate;
    private String returnDate;
    private String note;

    public static List<LoanGridDTO> convert(List<LoanHeaderDTO> loanHeaderDtos) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<LoanGridDTO> result = new ArrayList<>();
        for(LoanHeaderDTO loanHeaderDto : loanHeaderDtos) {
            result.add(new LoanGridDTO(
                    loanHeaderDto.getId(),
                    loanHeaderDto.getTitle(),
                    loanHeaderDto.getFullName(),
                    loanHeaderDto.getLoanDate() == null ? "-" : loanHeaderDto.getLoanDate().format(formatter),
                    loanHeaderDto.getDueDate() == null ? "-" : loanHeaderDto.getDueDate().format(formatter),
                    loanHeaderDto.getReturnDate() == null ? "-" : loanHeaderDto.getReturnDate().format(formatter),
                    loanHeaderDto.getNote()
            ));
        }
        return result;
    }

    public LoanGridDTO() {
    }

    public LoanGridDTO(Long id, String title, String fullName,
                       String loanDate, String dueDate, String returnDate,
                       String note) {
        this.id = id;
        this.title = title;
        this.fullName = fullName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.note = note;
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

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
