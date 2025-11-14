package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.*;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Category cannot be null")
    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0.01, message = "Price has to be greater than 0")
    private Double price;

    @NotNull(message = "Sku cannot be null")
    @NotBlank(message = "Sku cannot be blank")
    private String sku;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonManagedReference("inventory-product")
    private List<Inventory> inventory;

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
    
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getSku() {
        return sku;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }
    public List<Inventory> getInventory() {
        return inventory;
    }

    public Product() {}

    public Product(String name, String category, Double price, String sku) {
        setName(name);
        setCategory(category);
        setPrice(price);
        setSku(sku);
    }
}


