package com.Arnesto.Lab7.entities; // <-- **CRITICAL:** Use your actual full package name

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Lombok imports for brevity and cleaner code
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor; // Essential for JPA

// Import other entities in the same package


@Entity
@Table(name = "invoice")
@Getter // Generates all Getters
@Setter // Generates all Setters
@NoArgsConstructor // Generates the required JPA no-argument constructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use default value for the invoice date when created
    private LocalDate invoiceDate = LocalDate.now();

    // --- 1. Many-to-One: Link to Customer (Owning Side) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id", // Foreign key column in the 'invoice' table
            nullable = false
    )
    private Customer customer;

    // --- 2. Many-to-Many: Link to Products (Owning Side) ---
    // Note: Field name changed to conventional 'products' (lowercase 'p')
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "invoice_product", // The name of the join table
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();


    // --- Custom Constructor (Optional, but often useful for creation) ---
    public Invoice(Customer customer, Set<Product> products) {
        this.customer = customer;
        this.products = products;
        this.invoiceDate = LocalDate.now();
    }
}