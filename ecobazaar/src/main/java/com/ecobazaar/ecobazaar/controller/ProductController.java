package com.ecobazaar.ecobazaar.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecobazaar.ecobazaar.model.Product;
import com.ecobazaar.ecobazaar.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product updateProductDetails(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProductDetails(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductDetails(@PathVariable Long id) {
        productService.deleteProductDetails(id);
        return ResponseEntity.ok("Product has been deleted successfully.");
    }

    @GetMapping("/eco")
    public List<Product> getEcoCertified() {
        return productService.getEcoCertifiedProducts();
    }

    @GetMapping("/eco/sorted")
    public List<Product> getEcoCertifiedSorted() {
        return productService.getEcoCertifiedSortedByCarbonImpact();
    }
}
