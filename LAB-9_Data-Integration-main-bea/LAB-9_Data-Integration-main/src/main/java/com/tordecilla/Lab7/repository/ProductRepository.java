package com.Arnesto.Lab7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Arnesto.Lab7.entities.Product;

// Extends JpaRepository, specifying the Entity (Product) and the type of its primary key (Long)
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA automatically provides CRUD methods (save, findById, findAll, etc.).
}