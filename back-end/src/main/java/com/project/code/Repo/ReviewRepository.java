package com.project.code.Repo;

import com.project.code.Model.Review;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    // also mapped on its own
    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}
