package org.example.figma.repo;

import org.example.figma.entity.*;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    void findAllByArchivedFalse() {
        UUID id=UUID.randomUUID();
        Category category=Category.builder().id(id).build();
        categoryRepository.save(category);
        Product product=Product.builder().category(category).isArchived(false).build();
        productRepository.save(product);
        List<Product> allByArchivedFalse = productRepository.findAllByArchivedFalse();
        Assertions.assertFalse(allByArchivedFalse.isEmpty());

    }

    @Test
    void getTrendingProducts() {
        Product product=Product.builder().name("a").build();
        productRepository.save(product);
        OrderProduct orderProduct1=OrderProduct.builder().product(product).build();
        OrderProduct orderProduct2=OrderProduct.builder().product(product).build();
        OrderProduct orderProduct3=OrderProduct.builder().product(product).build();
        orderProductRepository.save(orderProduct1);
        orderProductRepository.save(orderProduct2);
        orderProductRepository.save(orderProduct3);
        List<TrendingOrderDto> trendingProducts = productRepository.getTrendingProducts();
        Assertions.assertFalse(trendingProducts.isEmpty());
    }
}