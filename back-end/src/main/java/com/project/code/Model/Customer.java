package com.project.code.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique id for the customer

    @Valid
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name; // name of the customer

    // @Email ?
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    private String email; // email of the customer

    @NotNull(message = "Phone cannot be null")
    @NotBlank(message = "Phone cannot be blank")
    @Pattern(
        regexp = "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$",
        message = "Invalid phone number format"
    )
    private String phone; // phone number of the customer
    
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetails> orderDetails; // list of orders made by the customer

    // Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    // Constructors
    public Customer() {}

    public Customer(String name, String email, String phone) {
        setName(name);
        setEmail(email);
        setPhone(phone);
    }
}

