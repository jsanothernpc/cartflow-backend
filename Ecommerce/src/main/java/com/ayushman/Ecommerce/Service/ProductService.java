package com.ayushman.Ecommerce.Service;

import com.ayushman.Ecommerce.Entity.Product;
import com.ayushman.Ecommerce.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


        @Autowired
        private ProductRepository productRepository;

     public List<Product> getAllProducts()
     {
          return productRepository.findAll();
     }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

     public Product addProduct(Product product)
     {
         return productRepository.save(product);
     }

    public void deleteProduct(Long id)
    {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
