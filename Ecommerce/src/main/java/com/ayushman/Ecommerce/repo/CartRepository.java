package com.ayushman.Ecommerce.repo;

import com.ayushman.Ecommerce.Entity.Cart;
import com.ayushman.Ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}