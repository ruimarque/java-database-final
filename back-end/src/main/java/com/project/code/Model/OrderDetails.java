package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.*;
import jakarta.persistance.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    @JsonManagedReference
    private Costumer costumer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private Store store;

    @Valid
    @NotNull(message = "Order price cannot be null")
    @Min(value = 0.01, message = "Order price has to br greater than zero")
    private Double totalPrice;

    @NotNull(message = "Date and time cannot be null")
    @NotBlank(message = "Date and time cannot be empty")
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }
    public Costumer getCostumer() {
        return costumer;
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

    public OrderDetails(Costumer costumer, Store store, Double totalPrice, LocalDateTime date) {
        setCostumer(costumer);
        setStore(store);
        setTotalPrice(totalPrice);
        setDate(date);
    }
}
