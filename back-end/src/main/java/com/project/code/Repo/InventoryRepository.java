package com.project.code.Repo;

import com.project.code.Model.Iventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface InventoryRepository extends JpaRepository<Iventory, Long> {

    @Query("SELECT i FROM Iventory i WHERE i.product_id = :productId AND i.store_id = :storeId")
    public Iventory findByProductIdandStoreId(Long productId, Long storeId);

    public List<Iventory> findByStore_Id(Long storeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Iventory i WHERE i.product_id = :productId")
    public void deleteByProductId(Long productId);
}
