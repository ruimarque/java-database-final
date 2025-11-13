package com.project.code.Service;

import com.project.code.Model.Customer;
import com.project.code.Model.OrderDetails;
import com.project.code.Model.OrderItem;
import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.PurchaseProductDTO;
import com.project.code.Model.Store;
import com.project.code.Model.Inventory;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderDetailsRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;

import org.springframework.beans.factory.annotation.AutoWired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @AutoWired
    private CustomerRepository customerRepository;
    @AutoWired
    private InventoryRepository inventoryRepository;
    @AutoWired
    private OrderDetailsRepository orderDetailsRepository;
    @AutoWired
    private OrderItemRepository orderItemRepository;
    @AutoWired
    private ProductRepository productRepository;
    @AutoWired
    private StoreRepository storeRepository;

    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {

        // placeOrderRequest holds all the relevant data
        
        // 1. Retrieve or create the customer
        Customer existingCustomer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail);
        Customer customer = new Customer();
        customer.setName(placeOrderRequest.getCustomerName());
        customer.setEmail(placeOrderRequest.getCustomerEmail());
        customer.setPhone(placeOrderRequest.getCustomerPhone());
        // if the customer from the request does not exist, save it to the database
        if(existingCustomer == null) {
            customer = customerRepository.save(customer);
        }
        else {
            customer = existingCustomer;
        }

        // 2. Retrieve the store
        // if it breaks compile replace this
        Store existingStore = storeRepository.findById(placeOrderRequest.getStoreId());
        if(existingStore == null) {
            throw new RuntimeException("Store not found"));
        }
        // with this
        // Store existingStore = storeRepository.findById(placeOrderRequest.getStoreId()).orElseThrow(() -> new RuntimeException("Store not found"));

        // 3. Create OrderDetails and save it to database
        OrderDetails newOrder = new OrderDetails(customer, store, placeOrderRequest.getTotalPrice(), LocalDateTime.now());
        newOrder = orderDetailsRepository.save(newOrder);

        // 4. Create and save OrderItems
        List<PurchaseProductDTO> purchasedItems = placeOrderRequest.getPurchaseProduct();
        for(PurchaseProductDTO purchasedProduct : purchasedItems) {
            // create the orderItem to save in the orderItemRepository
            OrderItem orderItem = new OrderItem();
            // find the iventory entry from the store and product id
            Iventory iventory = inventoryRepository.findByProductIdandStoreId(purchasedProduct.getId(), placeOrderRequest.getStoreId());            
            // update its stock level
            iventory.setStockLevel(iventory.getStockLevel()-purchasedProduct.getQuantity());
            // rewrite in database
            inventoryRepository.save(iventory);
            // link the order item with order details
            orderItem.setOrder(newOrder);
            // find the product in the database using the id, and set that Product object as the order item product
            orderItem.setProduct(productRepository.findById(purchasedProduct.getId()));
            // get the quantity and set it as the order item quantity
            orderItem.setQuantity(purchasedProduct.getQuantity());
            // get both quantity and price and multiply them to get the price to set in order item
            orderItem.setPrice(purchasedProduct.getQuantity()*purchasedProduct.getPrice());
            // save the order item
            orderItemRepository.save(orderItem);
        }
    }

            
// 1. **saveOrder Method**:
//    - Processes a customer's order, including saving the order details and associated items.
//    - Parameters: `PlaceOrderRequestDTO placeOrderRequest` (Request data for placing an order)
//    - Return Type: `void` (This method doesn't return anything, it just processes the order)

// 2. **Retrieve or Create the Customer**:
//    - Check if the customer exists by their email using `findByEmail`.
//    - If the customer exists, use the existing customer; otherwise, create and save a new customer using `customerRepository.save()`.

// 3. **Retrieve the Store**:
//    - Fetch the store by ID from `storeRepository`.
//    - If the store doesn't exist, throw an exception. Use `storeRepository.findById()`.

// 4. **Create OrderDetails**:
//    - Create a new `OrderDetails` object and set customer, store, total price, and the current timestamp.
//    - Set the order date using `java.time.LocalDateTime.now()` and save the order with `orderDetailsRepository.save()`.

// 5. **Create and Save OrderItems**:
//    - For each product purchased, find the corresponding inventory, update stock levels, and save the changes using `inventoryRepository.save()`.
//    - Create and save `OrderItem` for each product and associate it with the `OrderDetails` using `orderItemRepository.save()`.

   
}
