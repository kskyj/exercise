package com.example.exercise.service;

import com.example.exercise.domain.Customer;
import com.example.exercise.exception.DuplicatedException;
import com.example.exercise.exception.NotFoundException;
import com.example.exercise.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long saveCustomer(Customer customer) {
        validateDuplicatedCustomer(customer);
        String defaultStatus = "Active";
        if (customer.getStatus() == null) {
            customer.changeStatus(defaultStatus);
        }
        customerRepository.save(customer);
        return customer.getId();
    }

    @Transactional(readOnly = true)
    public void validateDuplicatedCustomer(Customer customer) {
        if (customerRepository.findByCustomerId(customer.getCustomerId()).isPresent()) {
            throw new DuplicatedException("Duplicated CustomerId");
        }
    }


    @Transactional(readOnly = true)
    public List<Customer> findCustomers() {
        return customerRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Customer retrieveByCustomerId(String customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Customer updateCustomer(String customerId, String status) {
        Customer findCustomer = retrieveByCustomerId(customerId);
        findCustomer.changeStatus(status);
        return findCustomer;
    }

    @Transactional
    public void deleteCustomer(String customerId) {
        Customer findCustomer = retrieveByCustomerId(customerId);
        customerRepository.delete(findCustomer);
    }
}
