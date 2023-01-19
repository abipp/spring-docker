package com.winterhold.repository;

import com.winterhold.dto.loan.LoanHeaderDTO;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
            SELECT new com.winterhold.dto.loan.LoanHeaderDTO(
            l.id, l.book.title,
            l.customer.fullName, l.loanDate, l.dueDate,
            l.returnDate, l.note)
            FROM Loan l
            WHERE l.book.title LIKE %:title%
                AND l.customer.fullName LIKE %:customerName%
            """)
    Page<LoanHeaderDTO> findAll(@Param("title") String title, @Param("customerName") String customerName,
                                Pageable pageable);

    Long countByBookCode(String code);

}
