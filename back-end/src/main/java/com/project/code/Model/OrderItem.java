package com.project.code.Model;

import jakarta.validation.*;
import jakarta.persistance.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private OrderDetails order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;

    @Valid
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity has to be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0.01, message = "Price has to be greater than zero")
    private Double price;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }
    public OrderDetails getOrder() {
        return order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }

    public OrderItem() {}

    public OrderItem(OrderDetails order, Product product, Integer quantity, Double price) {
        setOrder(order);
        setProduct(product);
        setQuantity(quantity);
        setPrice(price);
    }
}

