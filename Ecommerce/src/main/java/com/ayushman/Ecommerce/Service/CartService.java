package com.ayushman.Ecommerce.Service;

import com.ayushman.Ecommerce.Entity.Cart;
import com.ayushman.Ecommerce.Entity.CartItems;
import com.ayushman.Ecommerce.Entity.OrderItems;
import com.ayushman.Ecommerce.Entity.Orders;
import com.ayushman.Ecommerce.Entity.Product;
import com.ayushman.Ecommerce.Entity.User;
import com.ayushman.Ecommerce.repo.CartItemsRepository;
import com.ayushman.Ecommerce.repo.CartRepository;
import com.ayushman.Ecommerce.repo.OrderRepository;
import com.ayushman.Ecommerce.repo.ProductRepository;
import com.ayushman.Ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Cart addToCart(Long userId, Long productId, int quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Optional<CartItems> existingItem =
                cartItemsRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItems item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);

            cartItemsRepository.save(item);

        } else {

            CartItems item = new CartItems();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);

            cart.getCartItems().add(item);
        }

        return cartRepository.save(cart);
    }

    public Cart getCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void removeFromCart(Long cartItemId) {

        if (!cartItemsRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }

        cartItemsRepository.deleteById(cartItemId);
    }

    public void clearCart(Long userId) {

        Cart cart = getCart(userId);

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

    public Orders checkout(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setEmail(user.getEmail());
        order.setOrderDate(new Date());
        order.setStatus("PENDING");

        double total = 0.0;

        List<OrderItems> orderItems = new ArrayList<>();

        for (CartItems cartItem : cart.getCartItems()) {

            OrderItems orderItem = new OrderItems();
            orderItem.setOrders(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        Orders savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;

    }
}
