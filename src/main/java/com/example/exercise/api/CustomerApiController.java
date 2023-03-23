package com.example.exercise.api;

import com.example.exercise.domain.Customer;
import com.example.exercise.service.CustomerService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerResponse createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                .customerId(request.getCustomerId())
                .status(request.getStatus())
                .build();
        Long id = customerService.saveCustomer(customer);
        return new CreateCustomerResponse(id);
    }


    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers() {
        List<Customer> findMembers = customerService.findCustomers();
        return findMembers.stream()
                .map(m -> CustomerDto.builder()
                        .id(m.getId())
                        .customerId(m.getCustomerId())
                        .status(m.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomer(@PathVariable("customerId") String customerId) {
        Customer findMember = customerService.retrieveByCustomerId(customerId);
        return CustomerDto.builder()
                .id(findMember.getId())
                .customerId(findMember.getCustomerId())
                .status(findMember.getStatus())
                .build();
    }

    @PutMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateMemberResponse updateCustomer(@PathVariable("customerId") String customerId,
                                               @RequestBody @Valid UpdateMemberRequest request) {
        Customer customer = customerService.updateCustomer(customerId, request.getStatus());
        return UpdateMemberResponse.builder()
                .customerId(customer.getCustomerId())
                .status(customer.getStatus())
                .build();
    }

    @DeleteMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") String customerId) {
        customerService.deleteCustomer(customerId);
    }


//    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    static class CustomerDto {
        private Long id;
        private String customerId;
        private String status;
    }

//    @Data
    @Getter
    @RequiredArgsConstructor
    static class UpdateMemberRequest {
        @NotEmpty
        private String status;
    }

//    @Data
    @RequiredArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    static class UpdateMemberResponse {
        private String customerId;
        private String status;
    }

//    @Data
    @Getter
    @RequiredArgsConstructor
    static class CreateCustomerRequest {
        @NotEmpty
        private String customerId;
        private String status;
    }

//    @Data
    @Getter
    @RequiredArgsConstructor
    static class CreateCustomerResponse {
        private Long id;

        public CreateCustomerResponse(Long id) {
            this.id = id;
        }
    }
}