package com.winterhold.repository;

import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.CustomerHeaderDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerGridDTO(c.membershipNumber,
                c.firstName, c.lastName,
                CONCAT(c.firstName, ' ', c.lastName),
                c.birthDate,
                c.gender,
                c.phone,
                c.address,
                c.membershipExpiredDate)
            FROM Customer AS c
            WHERE c.membershipNumber LIKE %:id%
                AND CONCAT(c.firstName, ' ', c.lastName) LIKE %:fullName%
            """)
    Page<CustomerGridDTO> findAll(String id, String fullName, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerHeaderDTO
            (c.membershipNumber, c.fullName, c.membershipExpiredDate) 
            FROM Customer AS c
            WHERE c.membershipExpiredDate >= GETDATE()""")
    List<CustomerHeaderDTO> findAllCustomerNotExp();
}
