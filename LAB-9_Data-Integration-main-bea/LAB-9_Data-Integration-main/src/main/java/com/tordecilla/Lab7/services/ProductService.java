package com.Arnesto.Lab7.services;


import com.Arnesto.Lab7.entities.Product; // Corrected path to the Product entity
import com.Arnesto.Lab7.repository.ProductRepository;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This service is now database-backed
@Service
public class ProductService {

    // 1. Inject the Repository
    private final ProductRepository productRepository;

    // Constructor Injection (Spring automatically provides the implementation)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 2. Remove old in-memory fields (@PostConstruct, store map, idSeq)

    /**
     * Retrieves all products from the database.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds a product by its ID from the database.
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Creates a new product or updates an existing one.
     */
    public Product create(Product p) {
        return productRepository.save(p);
    }

    /**
     * Updates an existing product.
     */
    public Optional<Product> update(Long id, Product p) {
        // 1. Check if the product exists
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // 2. Update the fields
                    existingProduct.setName(p.getName());
                    existingProduct.setPrice(p.getPrice());

                    // 3. Save the updated entity
                    return productRepository.save(existingProduct);
                });
    }

    /**
     * Deletes a product by ID.
     */
    public boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}