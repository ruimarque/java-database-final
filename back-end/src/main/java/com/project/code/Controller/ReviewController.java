package com.project.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Customer;
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.ReviewRepository;
import com.project.code.Service.ServiceClass;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable long storeId, @PathVariable long productId) {
        Map<String, Object> map = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        List<Map<String, Object>> reviewsWithCustomerNames = new ArrayList<>();
        for(Review review : reviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("review", review.getComment());
            reviewMap.put("rating", review.getRating());
            Customer customer = customerRepository.findByid(review.getCustomerId());
            if(customer != null) {
                reviewMap.put("customerName", customer.getName());
            }
            else {
                reviewMap.put("customerName", "Unknown");
            }
            reviewsWithCustomerNames.add(reviewMap);
        }
        map.put("reviews", reviewsWithCustomerNames);
        return map;
    }
}
