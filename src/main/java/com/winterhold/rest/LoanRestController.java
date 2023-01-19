package com.winterhold.rest;

import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.LoanHeaderDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanRestController {

    private LoanService loanService;

    @Autowired
    public LoanRestController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(value={
            "",
            "/page={page}",
            "/name={name}",
            "/title={title}",
            "/page={page}/name={name}",
            "/page={page}/title={title}",
            "/name={name}/title={title}",
            "/page={page}&name={name}&title={title}"
    })
    public ResponseEntity<Object> get(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) String title,
            @PathVariable(required = false) String name){
        page = (page == null) ? 1 : page;
        name = (name == null) ? "" : name;
        title = (title == null) ? "" : title;
        try{
            Page<LoanHeaderDTO> allLoan = loanService.findAllLoan(page, title, name);
            List<LoanGridDTO> loanGrid = LoanGridDTO.convert(allLoan.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(loanGrid);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(
            @Valid @RequestBody UpsertLoanDTO dto,
            BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0L);
                Long response = loanService.save(dto);
                dto.setId(response);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(
            @Valid @RequestBody UpsertLoanDTO dto,
            BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                loanService.save(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/detail/id={id}")
    public ResponseEntity<Object> detail(@PathVariable() Long id) {
        try{
            LoanDetailDTO detailLoanById = loanService.getDetailLoanById(id);
            return ResponseEntity.status(HttpStatus.OK).body(detailLoanById);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/return/id={id}")
    public ResponseEntity<Object> returnBook(@PathVariable() Long id) {
        try{
            loanService.returnBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
