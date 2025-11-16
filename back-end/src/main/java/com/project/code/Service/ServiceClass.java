package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    // check if there is iventory for that product_id at store_id
    public boolean validateInventory(Inventory inventory){
        return inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(), inventory.getStore().getId()) == null;
    }

    // check if there is no product with the same name attribute in the database
    public boolean validateProduct(Product product) {
        // can check SKU aswell, if there are products with the same name, sku is unique
        return productRepository.findByName(product.getName()) == null;
    }

    // check if product exists by its id
    public boolean ValidateProductId(long id) {
        return productRepository.findByid(id) != null;
    }

    // search for and return iventory entry with associated id based on the product_id and store_id it relates to
    public Inventory getInventoryId(Inventory inventory) {
        Inventory result = inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(), inventory.getStore().getId());
        return result;
    }
}
