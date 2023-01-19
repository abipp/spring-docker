package com.winterhold.service;

import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.dto.loan.LoanHeaderDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {
    Page<LoanHeaderDTO> findAllLoan(Integer page, String title,
                                    String customerName);

    UpsertLoanDTO getLoanById(Long id);

    List<Book> findBookByLoanId(Long id);

    Long save(UpsertLoanDTO upsertLoanDTO);

    LoanDetailDTO getDetailLoanById(Long id);

    void returnBook(Long id);
}
