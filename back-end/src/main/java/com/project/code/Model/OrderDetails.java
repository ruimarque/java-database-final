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
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique id for the order

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer; // references the customer who placed the order via its id

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private Store store; // references the store at which the order was made via its id

    @Valid
    @NotNull(message = "Order price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Order price must be greater than 0")
    private Double totalPrice; // total price of the order

    @NotNull(message = "Date and time cannot be null")
    @Column(name = "date")
    private LocalDateTime date; // timestamp at which the order was made

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    public Store getStore() {
        return store;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderDetails() {}

    public OrderDetails(Customer customer, Store store, Double totalPrice, LocalDateTime date) {
        setCustomer(customer);
        setStore(store);
        setTotalPrice(totalPrice);
        setDate(date);
    }
}
