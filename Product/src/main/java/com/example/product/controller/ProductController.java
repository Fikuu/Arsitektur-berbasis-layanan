package com.example.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.example.product.model.ProductModel;
import com.example.product.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // =====================================================
    // CREATE PRODUCT
    // =====================================================
    @PostMapping
    public ProductModel createProduct(@RequestBody ProductModel product) {

        log.info(
                "CREATE_PRODUCT | STATUS=REQUEST | name={} | description={} | price={}",
                product.getName(),
                product.getDescription(),
                product.getPrice());

        ProductModel savedProduct = productService.createProduct(product);

        log.info(
                "CREATE_PRODUCT | STATUS=SUCCESS | id={} | name={} | price={}",
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice());

        return savedProduct;
    }

    // =====================================================
    // GET ALL PRODUCTS
    // =====================================================
    @GetMapping
    public List<ProductModel> getAllProducts() {

        log.info("GET_ALL_PRODUCTS | STATUS=REQUEST");

        List<ProductModel> products = productService.getAllProducts();

        log.info(
                "GET_ALL_PRODUCTS | STATUS=SUCCESS | totalProducts={}",
                products.size());

        return products;
    }

    // =====================================================
    // GET PRODUCT BY ID
    // =====================================================
    @GetMapping("/{id}")
    public ProductModel getProductById(@PathVariable Long id) {

        log.info(
                "GET_PRODUCT | STATUS=REQUEST | id={}",
                id);

        ProductModel product = productService.getProductById(id);

        log.info(
                "GET_PRODUCT | STATUS=SUCCESS | id={} | name={}",
                product.getId(),
                product.getName());

        return product;
    }

    // =====================================================
    // UPDATE PRODUCT
    // =====================================================
    @PutMapping("/{id}")
    public ProductModel updateProduct(
            @PathVariable Long id,
            @RequestBody ProductModel updatedProduct) {

        log.info(
                "UPDATE_PRODUCT | STATUS=REQUEST | id={} | name={} | price={}",
                id,
                updatedProduct.getName(),
                updatedProduct.getPrice());

        ProductModel product = productService.updateProduct(id, updatedProduct);

        log.info(
                "UPDATE_PRODUCT | STATUS=SUCCESS | id={} | name={} | price={}",
                product.getId(),
                product.getName(),
                product.getPrice());

        return product;
    }

    // =====================================================
    // DELETE PRODUCT
    // =====================================================
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        log.info(
                "DELETE_PRODUCT | STATUS=REQUEST | id={}",
                id);

        productService.deleteProduct(id);

        log.info(
                "DELETE_PRODUCT | STATUS=SUCCESS | id={}",
                id);

        return "Product deleted successfully";
    }

}