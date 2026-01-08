package com.Arnesto.Lab7.controllers;

import com.Arnesto.Lab7.entities.Invoice;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import com.Arnesto.Lab7.services.InvoiceService;

@RestController
@RequestMapping("/api/invoices") // Base path for all Invoice endpoints
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // --- Data Transfer Object (DTO) for the POST request body ---
    // This defines the structure expected for creating a new invoice
    static class InvoiceRequest {
        private Long customerId;
        private List<Long> productIds;

        // Getters and Setters (required for Spring to map JSON)
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public List<Long> getProductIds() { return productIds; }
        public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
    }


    // --- Controller Methods ---

    // POST /api/invoices
    // Endpoint to create a new invoice by linking a customer and products
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        try {
            Invoice newInvoice = invoiceService.createInvoice(
                    request.getCustomerId(),
                    request.getProductIds()
            );
            return ResponseEntity.status(201).body(newInvoice); // HTTP 201 Created
        } catch (RuntimeException e) {
            // Catches exceptions like "Customer not found" or "Product not found"
            return ResponseEntity.badRequest().build(); // HTTP 400 Bad Request
        }
    }

    // GET /api/invoices
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.findAll();
    }

    // GET /api/invoices/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.findById(id);

        // This is a crucial test point: when you retrieve the Invoice,
        // JPA will fetch the linked Customer and Products (lazy-loaded).
        return invoice.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ... other methods (e.g., PUT, DELETE)
}