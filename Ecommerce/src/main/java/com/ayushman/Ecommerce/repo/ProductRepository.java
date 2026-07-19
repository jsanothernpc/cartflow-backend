package com.ayushman.Ecommerce.repo;

import com.ayushman.Ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByPriceLessThanEqual(double price);

    List<Product> findByNameContainingIgnoreCase(String name);
}