package com.ayushman.Ecommerce.LLMTools;

import com.ayushman.Ecommerce.Entity.Product;
import com.ayushman.Ecommerce.repo.ProductRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductTool {

    private final ProductRepository productRepository;

    public ProductTool(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Tool(description = "Find products by name")
    public String getProductByName(String name) {

        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(name);

        if (products.isEmpty()) {
            return "No product found with name : " + name;
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(formatProduct(product))
                    .append("\n-----------------------------\n");
        }

        return sb.toString();
    }

    @Tool(description = "Get all products by category")
    public String getProductsByCategory(String category) {

        List<Product> products =
                productRepository.findByCategoryIgnoreCase(category);

        if (products.isEmpty()) {
            return "No products found in category : " + category;
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(formatProduct(product))
                    .append("\n-----------------------------\n");
        }

        return sb.toString();
    }

    @Tool(description = "Get products under a given price")
    public String getProductsUnderPrice(double price) {

        List<Product> products =
                productRepository.findByPriceLessThanEqual(price);

        if (products.isEmpty()) {
            return "No products found under price : " + price;
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(formatProduct(product))
                    .append("\n-----------------------------\n");
        }

        return sb.toString();
    }

    private String formatProduct(Product product) {

        return """
                Product Id : %d
                Name : %s
                Category : %s
                Price : %.2f
                Description : %s
                """
                .formatted(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getDescription()
                );
    }
}