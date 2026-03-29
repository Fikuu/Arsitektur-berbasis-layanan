package com.example.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.product.model.ProductModel;

public interface ProductRepo extends JpaRepository<ProductModel, Long> {

}
