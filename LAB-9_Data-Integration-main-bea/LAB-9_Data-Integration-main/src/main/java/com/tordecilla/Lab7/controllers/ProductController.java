package com.Arnesto.Lab7.controllers;


import com.Arnesto.Lab7.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Arnesto.Lab7.services.ProductService;


import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }


    // GET /api/products -> return all products (200 OK)
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }


    // GET /api/products/{id} -> return product or 404
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    // POST /api/products -> create, return 201 Created
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product created = service.create(product);
// Location header optional, build with created id
        URI location = URI.create(String.format("/api/products/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }


    // PUT /api/products/{id} -> update or 404
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product)
                .map(updated -> ResponseEntity.ok(updated))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    // DELETE /api/products/{id} -> delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = service.deleteById(id);
        if (removed) return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}