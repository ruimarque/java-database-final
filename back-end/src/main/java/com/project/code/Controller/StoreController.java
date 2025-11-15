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
import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Product;
import com.project.code.Model.Review;
import com.project.code.Model.Store;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.ReviewRepository;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.ServiceClass;
import com.project.code.Service.OrderService;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    OrderService orderService;

    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Map<String, String> map = new HashMap<>();
        try {
            Store existingStore = storeRepository.findByid(store.getId());
            if(existingStore != null) {
                map.put("message", "Store already exists");
                return map;
            }
            Store savedStore = storeRepository.save(store);
            map.put("message", "Store saved successfully with ID " + savedStore.getId());
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
        } catch (Exception e) {
            map.put("message", "Error: " + e);
        }
        return map;
    }

    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable long storeId) {
        Store store = storeRepository.findByid(storeId);
        if(store == null) {
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("/placeOrder")
    Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO order) {
        Map<String, String> map = new HashMap<>();
        try {
            orderService.saveOrder(order);
            map.put("null", "Order placed successfully");
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
        } catch (Exception e) {
            map.put("message", "Error: " + e);
        }
        return map;
    }
}
