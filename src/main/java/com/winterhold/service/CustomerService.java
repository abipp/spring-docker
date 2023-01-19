package com.winterhold.service;

import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.CustomerHeaderDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpsertCustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    Page<CustomerGridDTO> findAllCustomer(Integer page, String id, String fullName);

    UpsertCustomerDTO getCustomerById(String id);

    String insertCustomer(InsertCustomerDTO dto);

    void save(UpsertCustomerDTO upsertCustomerDTO);

    void deleteCustomerById(String id);

    void extendMemberShip(String id);

    List<CustomerHeaderDTO> findAllCustomerNotExp();
}
