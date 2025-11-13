package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;

@Service
public class ServiceClass {

    @AutoWired
    private InventoryRepository inventoryRepository;
    @AutoWired
    private ProductRepository productRepository;
    @AutoWired
    private StoreRepository storeRepository;

    public boolean validateInventory(Iventory iventory){
        Inventory result = inventoryRepository.findByProductIdandStoreId(iventory.getProduct().getId(), iventory.getStore().getId());
        if(result == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateProduct(Product product) {
        // can check SKU aswell, if there are products with the same name, sku is unique
        Product result = productRepository.findByName(product.getName());
        if(result == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean ValidateProductId(long id) {
        Product result = productRepository.findById(id);
        if(result == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public Iventory getIventoryId(Iventory iventory) {
        Inventory result = inventoryRepository.findByProductIdandStoreId(iventory.getProduct().getId(), iventory.getStore().getId());
        return result;
    }
}
