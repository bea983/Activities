package com.Arnesto.Lab7.controllers;

import com.Arnesto.Lab7.entities.Customer;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.Arnesto.Lab7.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers") // Base path for all Customer endpoints
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /api/customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    // POST /api/customers
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    // GET /api/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ... other methods (PUT, DELETE)
}