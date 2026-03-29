package com.example.product.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.product.model.ProductModel;
import com.example.product.repository.ProductRepo;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductModel createProduct(ProductModel product) {
        return productRepo.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return productRepo.findAll();
    }

    public ProductModel getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductModel updateProduct(Long id, ProductModel updatedProduct) {
        ProductModel existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        return productRepo.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
