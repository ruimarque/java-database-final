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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ServiceClass serviceClass;

    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product) {
        Map<String, String> map = new HashMap<>();
        // Check if product is already in the database
        if(!serviceClass.validateProduct(product)) {
            map.put("message", "Product is already registered on the database");
            return map;
        }

        // try to insert in database
        try {
            productRepository.save(product);
            map.put("message", "Product was registered on the database successfully");
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
        } catch (Exception e) {
            map.put("message", "Error: " + e);
        }
        return map;
    }

    @GetMapping("/product/{id}")
    public Map<String, Object> getProductById(@PathVariable long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("products", productRepository.findByid(id));
        return map;
    }

    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {
        Map<String, String> map = new HashMap<>();
        // verify if product exists
        if(serviceClass.validateProduct(product)) {
            map.put("message", "Product that you are trying to update does not exist");
            return map;
        }
        try {
            productRepository.save(product);
            map.put("message", "Product was updated successfully")   ;
        } catch (DataIntegrityViolationException dive) {
            map.put("message", "Error: " + dive);
        } catch (Exception e) {
            map.put("message", "Error: " + e);
        }
        return map;
    }

    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterByCategoryProduct(@PathVariable String name, @PathVariable String category) {
        Map<String, Object> map = new HashMap<>();
        if(name.equals("null")) {
            map.put("products", productRepository.findByCategory(category));
            return map;
        }
        else if(category.equals("null")) {
            map.put("products", productRepository.findProductBySubName(name));
            return map;
        }
        map.put("products", productRepository.findProductBySubNameAndCategory(name, category));
        return map;
    }

    @GetMapping
    public Map<String, Object> listProduct() {
        Map<String, Object> map = new HashMap<>();
        map.put("products", productRepository.findAll());
        return map;
    }

    @GetMapping("filter/{category}/{storeid}")
    public Map<String, Object> getProductByCategoryAndStoreId(@PathVariable String category, @PathVariable long storeid) {
        Map<String, Object> map = new HashMap<>();
        map.put("product", productRepository.findByCategoryAndStoreId(category, storeid));
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable long id) {
        Map<String, String> map = new HashMap<>();
        if(!serviceClass.ValidateProductId(id)) {
            map.put("message", "The product you are trying to delete does not exist");
            return map;
        }
        inventoryRepository.deleteByProductId(id);
        // do I need to delete from orders?
        productRepository.deleteById(id);
        map.put("message", "Product was deleted");
        return map;
    }

    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("products", productRepository.findProductBySubName(name));
        return map;
    } 
}
