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
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ServiceClass serviceClass;

    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {
        Product product = request.getProduct();
        Inventory inventory = request.getInventory();
        Map<String, String> map = new HashMap<String, String>();
        // Check if product id already exists
        if(!serviceClass.ValidateProductId(product.getId())) {
            map.put("message", "Product doesn't exist in the database");
            return map;
        }
        productRepository.save(product);
        map.put("message", "Updated product");

        if(inventory != null) {
            try {
                Inventory checkInventory = serviceClass.getInventoryId(inventory);
                // inventory exists for that product and is going to be updated
                if(checkInventory != null) {
                    inventory.setId(checkInventory.getId());
                    inventoryRepository.save(inventory);
                }
                else {
                    map.put("message", "No data available");
                    return map;
                }
            } catch (DataIntegrityViolationException dive) {
                map.put("message", "Error: " + dive);
                return map;
            } catch (Exception e) {
                map.put("message", "Error: " + e);
                return map;
            }
        }
        return map;
    }

    @PostMapping
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            if(serviceClass.validateInventory(inventory)) {
                // inventory entry does not exist
                inventoryRepository.save(inventory);
            }
            else {
                map.put("message", "Inventory entry already exists");
                return map;
            }   
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
            return map;
        } catch (Exception e) {
            map.put("message", "Error: " + e);
            return map;
        }  
        map.put("message", "Inventory entry was added successfully");
        return map;    
    }

    @GetMapping("/{storeId}")
    public Map<String, Object> getAllProducts(@PathVariable long storeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Product> products = productRepository.findProductsByStoreId(storeId);
        map.put("products", products);
        return map;
    }

    @GetMapping("filter/{category}/{name}/{storeid}")
    public Map<String, Object> getProductName(@PathVariable String category, @PathVariable String name, @PathVariable long storeid) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Product> products;
        if(category.equals("null")) {
            // filter by name in store with storeId
            products = productRepository.findByNameLike(storeid, name);
            map.put("product", products);
            return map;
        }
        else if(name.equals("null")) {
            // filter by category in store with storeId
            products = productRepository.findByCategoryAndStoreId(category, storeid);
            map.put("product", products);
            return map;
        }
        products = productRepository.findByNameAndCategory(storeid, name, category);
        map.put("product", products);
        return map;
    }

    @GetMapping("search/{name}/{storeid}")
    public Map<String, Object> searchProduct(@PathVariable String name, @PathVariable long storeid) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Product> products = productRepository.findByNameLike(storeid, name);
        map.put("product", products);
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable long id) {
        Map<String, String> map = new HashMap<String, String>();
        if(!serviceClass.ValidateProductId(id)) {
            map.put("message", "Product with that ID not present in database");
            return map;
        }
        inventoryRepository.deleteByProductId(id);
        map.put("message", "Product with that ID was successfully removed");
        return map;
    }

    @GetMapping("validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(@PathVariable int quantity, @PathVariable long storeId, @PathVariable long productId) {
        int quantityInStore = inventoryRepository.findByProductIdandStoreId(productId, storeId).getStockLevel();
        if(quantityInStore >= quantity) {
            return true;
        }
        return false;
    }
}
