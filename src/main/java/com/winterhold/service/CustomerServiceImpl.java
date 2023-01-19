package com.winterhold.service;

import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.CustomerHeaderDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpsertCustomerDTO;
import com.winterhold.entity.Customer;
import com.winterhold.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    private final int rowsInPage = 10;

    @Override
    public Page<CustomerGridDTO> findAllCustomer(Integer page, String id, String fullName) {
        Pageable pagination = PageRequest.of((page - 1), rowsInPage, Sort.by("id"));
        return customerRepository.findAll(id, fullName, pagination);
    }

    @Override
    public UpsertCustomerDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer dengan Id tersebut tidak ditemukan"));
        return new UpsertCustomerDTO(customer.getMembershipNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate(),
                customer.getGender(),
                customer.getPhone(),
                customer.getAddress());
    }

    @Override
    public String insertCustomer(InsertCustomerDTO dto) {
        Customer customer = new Customer(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getExpiredDate()
        );
        Customer response = customerRepository.save(customer);
        return response.getMembershipNumber();
    }

    @Override
    public void save(UpsertCustomerDTO upsertCustomerDTO) {
        Customer customer = new Customer(
                upsertCustomerDTO.getId(),
                upsertCustomerDTO.getFirstName(),
                upsertCustomerDTO.getLastName(),
                upsertCustomerDTO.getBirthDate(),
                upsertCustomerDTO.getGender(),
                upsertCustomerDTO.getPhone(),
                upsertCustomerDTO.getAddress(),
                upsertCustomerDTO.getExpiredDate()
        );
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void extendMemberShip(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer dengan ID tersebut tidak ditemukan"));
        if(LocalDate.now().isAfter(customer.getMembershipExpiredDate())){
            customer.setMembershipExpiredDate(LocalDate.now().plusYears(2));
        }
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerHeaderDTO> findAllCustomerNotExp() {
        return customerRepository.findAllCustomerNotExp();
    }
}
