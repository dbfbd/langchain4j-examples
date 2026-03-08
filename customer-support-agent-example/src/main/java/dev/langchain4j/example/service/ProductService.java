package dev.langchain4j.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.langchain4j.example.entity.Product;
import dev.langchain4j.example.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for product-related operations
 */
@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * Find product by name (partial match)
     */
    public Product findByName(String name) {
        return productMapper.selectOne(
                new LambdaQueryWrapper<Product>()
                        .like(Product::getName, name)
                        .last("LIMIT 1")
        );
    }

    /**
     * Search products by category
     */
    public List<Product> findByCategory(String category) {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .like(Product::getCategory, category)
        );
    }
}
