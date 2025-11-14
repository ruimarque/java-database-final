package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id; // MongoDB id string

    @NotNull(message = "Customer ID cannot be null")
    @Min(value = 0, message = "Customer ID cannot be negative")
    private Long customerId;

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 0, message = "Product ID cannot be negative")
    private Long productId;

    @NotNull(message = "Store ID cannot be null")
    @Min(value = 0, message = "Store ID cannot be negative")
    private Long storeId;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 0, message = "Rating cannot be less than 0")
    @Max(value = 5, message = "Rating cannot be greater than 5")
    private Byte rating;

    private String comment; // optional, does it need any validation?

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getProductId() {
        return productId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public Long getStoreId() {
        return storeId;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }
    public Byte getRating() {
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }

    public Review() {}

    public Review(Long customerId, Long productId, Long storeId, Byte rating) {
        setCustomerId(customerId);
        setProductId(productId);
        setStoreId(storeId);
        setRating(rating);
    }

    public Review(Long customerId, Long productId, Long storeId, Byte rating, String comment) {
        setCustomerId(customerId);
        setProductId(productId);
        setStoreId(storeId);
        setRating(rating);
        setComment(comment);
    }
}
