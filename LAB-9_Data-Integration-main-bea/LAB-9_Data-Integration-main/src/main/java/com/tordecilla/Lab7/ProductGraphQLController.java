package com.Arnesto.Lab7;

import com.Arnesto.Lab7.entities.Product;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import com.Arnesto.Lab7.services.ProductService;

import java.util.List;
import java.util.Optional;

// This class will handle all GraphQL operations
@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERY MAPPINGS (READ OPERATIONS) ---

    // Maps to the 'allProducts' Query in the schema
    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    // Maps to the 'productById' Query in the schema
    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    // --- MUTATION MAPPINGS (WRITE OPERATIONS) ---

    // Maps to the 'createProduct' Mutation
    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        // CHANGE: Use 'create' to match the service method name
        return productService.create(newProduct);
    }

    // Maps to the 'updateProduct' Mutation
    @MutationMapping
    public Optional<Product> updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        Product updatedProductDetails = new Product();
        updatedProductDetails.setName(name);
        updatedProductDetails.setPrice(price);
        // The service layer handles setting the ID and updating the map
        return productService.update(id, updatedProductDetails);
    }

    // Maps to the 'deleteProduct' Mutation
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteById(id);
    }
}
