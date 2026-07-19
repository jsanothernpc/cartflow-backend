package com.ayushman.Ecommerce.repo;

import com.ayushman.Ecommerce.Entity.Cart;
import com.ayushman.Ecommerce.Entity.CartItems;
import com.ayushman.Ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);

}