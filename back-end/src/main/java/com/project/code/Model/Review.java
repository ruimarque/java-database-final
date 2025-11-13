package com.project.code.Model;

import jakarta.validation.*;
import jakarta.persistance.*;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id; // MongoDB id string

    @NotNull(message = "Costumer ID cannot be null")
    @Min(value = 0, message = "Costumer ID cannot be negative")
    private Long customerId;

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 0, message = "Product ID cannot be negative")
    private Long productId;

    @NotNull(message = "Store ID cannot be null")
    @Min(value = 0, message = "Store ID cannot be negative")
    private Long StoreId;

    @NotNull(message = "Product ID cannot be null")
    @Min(value = 0, message = "Product ID cannot be negative")
    private Long productId;

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

    public void setCostumerId(Long costumerId) {
        this.costumerId = costumerId;
    }
    public Long getCostumerId() {
        return costumerId;
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

    public Review(Long costumerId, Long productId, Long storeId, Byte rating) {
        setCostumerId(costumerId);
        setProductId(productId);
        setStoreId(storeId);
        setRating(rating);
    }

    public Review(Long costumerId, Long productId, Long storeId, Byte rating, String comment) {
        setCostumerId(costumerId);
        setProductId(productId);
        setStoreId(storeId);
        setRating(rating);
        setComment(comment);
    }
}
