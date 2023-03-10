package com.example.jpaapp.service;

import com.example.jpaapp.domain.Customer;
import com.example.jpaapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String customerId, String Status) {
        Customer customer = validateCustomer(customerId, Status);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer validateCustomer(String customerId, String status) {
        String defaultStatus = "Active";
        if (!StringUtils.hasText(status)) {
            status = defaultStatus;
        }
        try {
            Customer findCustomer = retrieveByCustomerId(customerId);
            findCustomer.setStatus(status);
            return findCustomer;
        } catch (IllegalArgumentException e) {
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setStatus(status);
            return customer;
        }
    }


    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }


    public Customer retrieveByCustomerId(String customerId) throws IllegalArgumentException {
        return customerRepository.findByCustomerId(customerId).orElseThrow(IllegalArgumentException::new);
    }


//    @Transactional
//    public void update(Long id, String name) {
//        Member findMember = customerRepository.findOne(id);
//        findMember.setName(name);
//    }
}
