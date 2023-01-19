package com.winterhold.rest;

import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.UpsertCustomerDTO;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value={
            "",
            "/page={page}",
            "/name={name}",
            "/id={id}",
            "/page={page}&name={name}",
            "/page={page}&id={id}",
            "/name={name}&id={id}",
            "/page={page}&name={name}&id={id}"
    })
    public ResponseEntity<Object> get(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) String id,
            @PathVariable(required = false) String name){
        page = (page == null) ? 1 : page;
        name = (name == null) ? "" : name;
        id = (id == null) ? "" : id;
        try{
            Page<CustomerGridDTO> allCustomer = customerService.findAllCustomer(page, id, name);
            return ResponseEntity.status(HttpStatus.OK).body(allCustomer.getContent());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(
            @Valid @RequestBody UpsertCustomerDTO dto,
            BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                customerService.save(dto);
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
            @Valid @RequestBody UpsertCustomerDTO dto,
            BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                customerService.save(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/extend/id={id}")
    public ResponseEntity<Object> extend(@PathVariable() String id) {
        try{
            customerService.extendMemberShip(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/detail/id={id}")
    public ResponseEntity<Object> detail(@PathVariable() String id) {
        try{
            UpsertCustomerDTO customer = customerService.getCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable() String id){
        try{
            customerService.deleteCustomerById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}
