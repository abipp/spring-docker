package com.winterhold.controller;

import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.UpsertCustomerDTO;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/index")
    public String getAllCustomer(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "") String id,
                                 @RequestParam(defaultValue = "") String fullName,
                                 Model model) {
        Page<CustomerGridDTO> allCustomer = customerService.findAllCustomer(page, id, fullName);
        model.addAttribute("customers", allCustomer);
        model.addAttribute("breadCrumbs", "Customer / Index");
        model.addAttribute("totalPage", allCustomer.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("id", id);
        model.addAttribute("fullName", fullName);
        return "customer/customer-index";
    }

    @GetMapping("/upsert-form")
    public String upsertForm(@RequestParam(required = false) String id, Model model){
        if(id != null){
            model.addAttribute("customers", customerService.getCustomerById(id));
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Customer / Update");
        }
        else {
            model.addAttribute("customers", new UpsertCustomerDTO());
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Customer / Insert");
        }
        return "/customer/customer-form";
    }

    @PostMapping("/upsert")
    public String save(@Valid @ModelAttribute("customers") UpsertCustomerDTO dto,
                       BindingResult bindingResult,
                       Model model){
        if (bindingResult.hasErrors()){
            if (dto.getId() != null){
                model.addAttribute("type", "update");
                model.addAttribute("breadCrumbs", "Customer / Update");
            } else {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Customer / Insert");
            }
            return "/customer/customer-form";
        } else {
            customerService.save(dto);
            return "redirect:/customer/index";
        }
    }

    @GetMapping("/extend")
    public String extend(@RequestParam String id){
        customerService.extendMemberShip(id);
        return "redirect:/customer/index";
    }

    @GetMapping("/delete")
    public String delete (@RequestParam String id){
        customerService.deleteCustomerById(id);
        return "redirect:/customer/index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam String id, Model model){
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("breadCrumbs", "Customer / Membership Detail");
        return "/customer/customer-detail";
    }
}
