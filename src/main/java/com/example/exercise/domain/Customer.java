package com.example.exercise.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String customerId;

    private String status;


    @Builder
    public Customer(String customerId, String status){
        this.customerId = customerId;
        this.status = status;
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}
