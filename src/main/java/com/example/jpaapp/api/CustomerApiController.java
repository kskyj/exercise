package com.example.jpaapp.api;

import com.example.jpaapp.domain.Customer;
import com.example.jpaapp.domain.Member;
import com.example.jpaapp.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;


    @PostMapping("/api/v1/customers")
    public CreateCustomerResponse saveCustomer(@RequestBody @Valid CreateCustomerRequest request) {

        Customer customer = new Customer();
        customer.setCustomerId(request.getCustomerId());

        Customer customerCreateResult = customerService.createCustomer(customer.getCustomerId(), customer.getStatus());
        return new CreateCustomerResponse(customerCreateResult.getId());
    }


    @GetMapping("/api/v1/customers")
    public Result customers() {
        List<Customer> findMembers = customerService.findCustomers();
        List<CustomerDto> collect = findMembers.stream()
                .map(m -> new CustomerDto(m.getCustomerId(), m.getStatus()))
                .collect(Collectors.toList());

        return new Result<>(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class CustomerDto {
        private String customerId;
        private String status;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateCustomerRequest {
        @NotEmpty
        private String customerId;
    }

    @Data
    static class CreateCustomerResponse {
        private Long id;

        public CreateCustomerResponse(Long id) {
            this.id = id;
        }
    }
}