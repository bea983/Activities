package com.Arnesto.Lab7.services;

import com.Arnesto.Lab7.entities.Customer; // Import the Customer entity
import com.Arnesto.Lab7.repository.CustomerRepository; // Import the CustomerRepository
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    // Inject the Repository
    private final CustomerRepository customerRepository;

    // Constructor Injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Finds a customer by ID.
     */
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Creates a new customer (or updates an existing one).
     */
    public Customer create(Customer customer) {
        // Use the save method to persist the entity
        return customerRepository.save(customer);
    }

    /**
     * Updates an existing customer's details.
     */
    public Optional<Customer> update(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // Update fields
                    existingCustomer.setName(updatedCustomer.getName());
                    existingCustomer.setEmail(updatedCustomer.getEmail());

                    // Save the changes back to the database
                    return customerRepository.save(existingCustomer);
                });
    }

    /**
     * Deletes a customer by ID.
     */
    public boolean deleteById(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}