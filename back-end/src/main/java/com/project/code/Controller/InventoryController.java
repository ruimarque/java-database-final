package com.project.code.Controller;

import org.springframework.beans.factory.annotation.AutoWired;
import org.springframework.dao.DataIntegrityViolationException;

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
@RequestMapping("/iventory")
public class InventoryController {

    @AutoWired
    ProductRepository productRepository;
    @AutoWired
    InventoryRepository inventoryRepository;
    @AutoWired
    ServiceClass serviceClass;

    @PutMapping
    public Map<String, String> updateIventory(@RequestBody CombinedRequest request) {
        Product product = request.getProduct();
        Iventory iventory = request.getInventory();
        Map<String, String> map = new HashMap<String, String>();
        // Check if product id already exists
        if(!serviceClass.ValidateProductId(product.getId())) {
            map.put("message", "Product doesn't exist in the database");
            return map;
        }
        productRepository.save(product);
        map.put("message", "Updated product");

        if(iventory != null) {
            try {
                Iventory checkIventory = serviceClass.getInventoryId(iventory);
                // iventory exists for that product and is going to be updated
                if(checkIventory != null) {
                    iventory.setId(checkIventory.getId();
                    inventoryRepository.save(iventory);
                }
                else {
                    map.put(message, "No data available");
                    return map;
                }
            } catch (DataIntegrityViolationException dive) {
                map.put(message, "Error: " + dive);
                return map;
            } catch (Exception e) {
                map.put(message, "Error: " + e);
                return map;
            }
        }
        return map;
    }

    @PostMapping
    public Map<String, String> saveIventory(@RequestBody Iventory iventory) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            if(serviceClass.validateInventory(iventory)) {
                // iventory entry does not exist
                inventoryRepository.save(iventory);
            }
            else {
                map.put(message, "Iventory entry already exists");
                return map;
            }   
        } catch (DataIntegrityViolationException dive) {
            map.put(message, "Error: " + dive);
            return map;
        } catch (Exception e) {
            map.put(message, "Error: " + e);
            return map;
        }  
        map.put(message, "Iventory entry was added successfully");
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




// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to indicate that this is a REST controller, which handles HTTP requests and responses.
//    - Use `@RequestMapping("/inventory")` to set the base URL path for all methods in this controller. All endpoints related to inventory will be prefixed with `/inventory`.


// 2. Autowired Dependencies:
//    - Autowire necessary repositories and services:
//      - `ProductRepository` will be used to interact with product data (i.e., finding, updating products).
//      - `InventoryRepository` will handle CRUD operations related to the inventory.
//      - `ServiceClass` will help with the validation logic (e.g., validating product IDs and inventory data).


// 3. Define the `updateInventory` Method:
//    - This method handles HTTP PUT requests to update inventory for a product.
//    - It takes a `CombinedRequest` (containing `Product` and `Inventory`) in the request body.
//    - The product ID is validated, and if valid, the inventory is updated in the database.
//    - If the inventory exists, update it and return a success message. If not, return a message indicating no data available.


// 4. Define the `saveInventory` Method:
//    - This method handles HTTP POST requests to save a new inventory entry.
//    - It accepts an `Inventory` object in the request body.
//    - It first validates whether the inventory already exists. If it exists, it returns a message stating so. If it doesn’t exist, it saves the inventory and returns a success message.


// 5. Define the `getAllProducts` Method:
//    - This method handles HTTP GET requests to retrieve products for a specific store.
//    - It uses the `storeId` as a path variable and fetches the list of products from the database for the given store.
//    - The products are returned in a `Map` with the key `"products"`.


// 6. Define the `getProductName` Method:
//    - This method handles HTTP GET requests to filter products by category and name.
//    - If either the category or name is `"null"`, adjust the filtering logic accordingly.
//    - Return the filtered products in the response with the key `"product"`.


// 7. Define the `searchProduct` Method:
//    - This method handles HTTP GET requests to search for products by name within a specific store.
//    - It uses `name` and `storeId` as parameters and searches for products that match the `name` in the specified store.
//    - The search results are returned in the response with the key `"product"`.


// 8. Define the `removeProduct` Method:
//    - This method handles HTTP DELETE requests to delete a product by its ID.
//    - It first validates if the product exists. If it does, it deletes the product from the `ProductRepository` and also removes the related inventory entry from the `InventoryRepository`.
//    - Returns a success message with the key `"message"` indicating successful deletion.


// 9. Define the `validateQuantity` Method:
//    - This method handles HTTP GET requests to validate if a specified quantity of a product is available in stock for a given store.
//    - It checks the inventory for the product in the specified store and compares it to the requested quantity.
//    - If sufficient stock is available, return `true`; otherwise, return `false`.

}
