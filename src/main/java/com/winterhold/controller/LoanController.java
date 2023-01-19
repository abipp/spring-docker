package com.winterhold.controller;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.customer.CustomerHeaderDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.LoanHeaderDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.BookService;
import com.winterhold.service.CustomerService;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {

    private LoanService loanService;
    private CustomerService customerService;
    private BookService bookService;

    @Autowired
    public LoanController(LoanService loanService, CustomerService customerService, BookService bookService) {
        this.loanService = loanService;
        this.customerService = customerService;
        this.bookService= bookService;
    }

    @GetMapping("/index")
    public String getAllLoan(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "") String title,
                             @RequestParam(defaultValue = "") String fullName,
                             Model model){
        Page<LoanHeaderDTO> allLoan = loanService.findAllLoan(page, title, fullName);
        List<LoanGridDTO> loanGrid = LoanGridDTO.convert(allLoan.getContent());

        model.addAttribute("loans", loanGrid);
        model.addAttribute("breadCrumbs", "Loan / Index");
        model.addAttribute("totalPage", allLoan.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("fullName", fullName);
        return "/loan/loan-index";
    }

    @GetMapping("/upsert-form")
    public String upsertForm (@RequestParam(required = false) Long id, Model model){
        model.addAttribute("dropdownCustomer", customerService.findAllCustomerNotExp());
        if(id != null){
            model.addAttribute("dropdownTitle", bookService.findAllBookAvailable());
            model.addAttribute("loans", loanService.getLoanById(id));
            model.addAttribute("breadCrumbs", "Loan / Update");
        }else {
            model.addAttribute("dropdownTitle", bookService.findAllBookAvailable());
            model.addAttribute("loans", new UpsertLoanDTO());
            model.addAttribute("breadCrumbs", "Loan / Insert");
        }
        return "/loan/loan-form";
    }

    @PostMapping("/upsert")
    public String upsertForm(@Valid @ModelAttribute("loans") UpsertLoanDTO loan,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            List<CustomerHeaderDTO> customers = customerService.findAllCustomerNotExp();
            List<BookGridDTO> books = bookService.findAllBookAvailable();
            model.addAttribute("dropdownCustomer", customers);
            model.addAttribute("dropdownTitle", books);
            if (loan.getId() != null){
                model.addAttribute("breadCrumbs", "Loan / Update");
            } else {
                model.addAttribute("breadCrumbs", "Loan / Insert");
            }
            return "/loan/loan-form";
        } else {
            loanService.save(loan);
            return "redirect:/loan/index";
        }

    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("loans", loanService.getDetailLoanById(id));
            model.addAttribute("breadCrumbs", "Loan / Detail");
        }
        return "/loan/loan-detail";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam(required = false) Long id) {
        if (id != null) {
            loanService.returnBook(id);
        }
        return "redirect:/loan/index";
    }
}
