package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.*;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("inventory-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference("inventory-store")
    private Store store;

    @NotNull(message = "Stock leve cannot be null")
    @Min(value = 0, message = "Stock level can't be negative")
    private Integer stockLevel;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    public Store getStore() {
        return store;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;        
    }
    public Integer getStockLevel() {
        return stockLevel;
    }

    public Inventory() {}

    public Inventory(Product product, Store store, Integer stockLevel) {
        setProduct(product);
        setStore(store);
        setStockLevel(stockLevel);
    }
}

