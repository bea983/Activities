package com.Arnesto.Lab7.entities; // <-- Use your full package name

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

// --- ADD THESE LOMBOK ANNOTATIONS ---
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
// ------------------------------------

@Entity
@Table(name = "customer")
@Getter // Generates all Getters (fixes getName(), getEmail(), getId())
@Setter // Generates all Setters (fixes setName(), setEmail(), setId())
@NoArgsConstructor // Generates the required JPA no-arg constructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Getters/Setters for this are now generated
    private String email; // Getters/Setters for this are now generated

    // Remove the individual @Getter if you use the class-level @Getter
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invoice> invoices = new ArrayList<>();

    // ... rest of the class
}