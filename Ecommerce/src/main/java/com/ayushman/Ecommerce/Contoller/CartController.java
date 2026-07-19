package com.ayushman.Ecommerce.Contoller;

import com.ayushman.Ecommerce.Entity.Cart;
import com.ayushman.Ecommerce.Entity.Orders;
import com.ayushman.Ecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {

        return cartService.getCart(userId);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {

        cartService.removeFromCart(cartItemId);

        return "Item removed from cart successfully";
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable Long userId) {

        cartService.clearCart(userId);

        return "Cart cleared successfully";
    }

    @PostMapping("/checkout/{userId}")
    public Orders checkout(@PathVariable Long userId) {

        return cartService.checkout(userId);
    }
}