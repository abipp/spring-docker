package com.winterhold.service;

import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.dto.loan.LoanHeaderDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import com.winterhold.entity.Loan;
import com.winterhold.repository.BookRepository;
import com.winterhold.repository.CustomerRepository;
import com.winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final int rowsInPage = 10;

    @Override
    public Page<LoanHeaderDTO> findAllLoan(Integer page, String title, String customerName) {
        Pageable pagination = PageRequest.of((page - 1), rowsInPage, Sort.by("id"));
        return loanRepository.findAll(title, customerName, pagination);
    }

    @Override
    public UpsertLoanDTO getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan dengan ID tersebut tidak ditemukan"));
        return new UpsertLoanDTO(
                loan.getId(),
                loan.getCustomerNumber(),
                loan.getBookCode(),
                loan.getLoanDate(),
                loan.getNote());
    }

    @Override
    public List<Book> findBookByLoanId(Long id) {
        return bookRepository.findByLoansId(id);
    }

    @Override
    public Long save(UpsertLoanDTO upsertLoanDTO) {
        Customer customer = customerRepository.findById(upsertLoanDTO.getCustomerNumber())
                .orElseThrow(() -> new IllegalArgumentException("Customer dengan ID tersebut tidak ditemukan"));
        Book book = bookRepository.findById(upsertLoanDTO.getBookCode())
                .orElseThrow(() -> new IllegalArgumentException("Book dengan ID tersebut tidak ditemukan"));
        book.setBorrowed(true);
        Loan loan = new Loan(
                upsertLoanDTO.getId(),
                customer.getMembershipNumber(),
                book.getCode(),
                upsertLoanDTO.getLoanDate(),
                upsertLoanDTO.getLoanDate().plusDays(5),
                upsertLoanDTO.getNote()
        );
        bookRepository.save(book);
        Loan response = loanRepository.save(loan);
        return response.getId();
    }

    @Override
    public LoanDetailDTO getDetailLoanById(Long id) {
        Loan data = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan dengan ID tersebut tidak ditemukan"));
        return new LoanDetailDTO(
                data.getId(),
                data.getCustomerNumber(),
                data.getCustomer().getFullName(),
                data.getCustomer().getPhone(),
                data.getCustomer().getMembershipExpiredDate(),
                data.getBook().getTitle(),
                data.getBook().getCategory().getName(),
                data.getBook().getAuthor().getFullName(),
                data.getBook().getCategory().getFloor(),
                data.getBook().getCategory().getIsle(),
                data.getBook().getCategory().getBay());
    }

    @Override
    public void returnBook(Long id) {
        Loan data = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        Book book = data.getBook();
        book.setBorrowed(false);
        bookRepository.save(book);
        data.setReturnDate(LocalDate.now());
        loanRepository.save(data);
    }
}
