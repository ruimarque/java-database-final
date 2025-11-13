package com.project.code.Repo;

import com.project.code.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // simple queries are mapped by findBy
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findBySku(String sku);
    Product findByName(String name);
    Product findById(Long id);
    @Query("SELECT i.product FROM Iventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Product> findByNameLike(Long storeId, String pname);
    @Query("SELECT i.product FROM Iventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.product.category = :category")
    List<Product> findByNameAndCategory(Long storeId, String pname, String category);
    @Query("SELECT i.product FROM Iventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    List<Product> findByCategoryAndStoreId(String category, Long storeId);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Product> findProductBySubName(String pname);
    @Query("SELECT i.product FROM Iventory i WHERE i.store.id = :storeId")
    List<Product> findProductsByStoreId(Long storeId);
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findProductsByCategory(String category);
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND p.category = :category")
    List<Product> findProductBySubNameAndCategory(String pname, String category);
}
