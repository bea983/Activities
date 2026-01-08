package com.Arnesto.Lab7.services;

import com.Arnesto.Lab7.entities.Customer;
import com.Arnesto.Lab7.entities.Invoice;
import com.Arnesto.Lab7.entities.Product;
import com.Arnesto.Lab7.repository.CustomerRepository;
import com.Arnesto.Lab7.repository.InvoiceRepository;
import com.Arnesto.Lab7.repository.ProductRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Important for ensuring complex operations succeed or fail together

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository; // Needed to find the customer
    private final ProductRepository productRepository;   // Needed to find the products

    // Constructor Injection for all three repositories
    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository customerRepository,
                          ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    /**
     * Creates a new Invoice and links it to an existing Customer and list of Products.
     */
    @Transactional
    public Invoice createInvoice(Long customerId, List<Long> productIds) {
        // 1. Find the Customer (Many-to-One side)
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // 2. Find the Products (Many-to-Many side)
        // findAllById retrieves all entities whose IDs are in the list
        List<Product> foundProducts = productRepository.findAllById(productIds);

        // Basic check to ensure all product IDs were valid
        if (foundProducts.size() != productIds.size()) {
            throw new RuntimeException("One or more products specified were not found in the database.");
        }

        Set<Product> products = new HashSet<>(foundProducts);

        // 3. Create and populate the new Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now());

        // Set relationships
        invoice.setCustomer(customer);  // Sets the customer_id FK on the invoice table
        invoice.setProducts(products);  // Populates the invoice_product join table

        // 4. Save the Invoice
        return invoiceRepository.save(invoice);
    }

    // --- Basic CRUD for Invoice ---

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findById(Long id) {
        // Optional: you can fetch related entities explicitly here if needed
        return invoiceRepository.findById(id);
    }

    // ... add update/delete methods if required
}