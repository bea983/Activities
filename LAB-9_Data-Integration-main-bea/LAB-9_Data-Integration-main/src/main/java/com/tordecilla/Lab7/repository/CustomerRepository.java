package com.Arnesto.Lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Arnesto.Lab7.entities.Customer;

// Extends JpaRepository, specifying the Entity (Customer) and the type of its primary key (Long)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // No code needed here, standard methods are inherited.
}