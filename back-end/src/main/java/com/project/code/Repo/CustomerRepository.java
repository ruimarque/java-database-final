package com.project.code.Repo;

import com.project.code.Model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // @Query("SELECT * FROM Customer c WHERE c.email = :email") is not needed because the query is 
    // simple enough that Spring Data JPA can automatically generate it based on method name
    public Customer findByEmail(String email);

    public Customer findById(Long id);  
}


