package com.example.product.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.product.model.ProductModel;
import com.example.product.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE
    @PostMapping
    public ProductModel createProduct(@RequestBody ProductModel product) {
        return productService.createProduct(product);
    }

    // READ ALL
    @GetMapping
    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ProductModel getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductModel updateProduct(
            @PathVariable Long id,
            @RequestBody ProductModel updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

}