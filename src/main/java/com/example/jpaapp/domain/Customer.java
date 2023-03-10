package com.example.jpaapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String customerId;

    private String status;

}
