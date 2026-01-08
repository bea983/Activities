package com.Arnesto.Lab7.repository; // Ensure this is the correct package for your repositories

import org.springframework.data.jpa.repository.JpaRepository;
import com.Arnesto.Lab7.entities.Invoice; // CORRECTED: Assuming the entity package is 'entities' (plural)

// The interface must be defined as a top-level element,
// and its name must match the file name.
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // Spring Data JPA automatically provides methods like save(), findById(), findAll(), etc.

    // You do NOT need any extra code here unless you add custom queries.

}