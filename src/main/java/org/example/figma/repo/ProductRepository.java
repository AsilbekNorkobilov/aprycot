package org.example.figma.repo;

import org.example.figma.entity.Product;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("from Product p where p.isArchived=false")
    List<Product> findAllByArchivedFalse();

    @Query("select new org.example.figma.model.dto.response.TrendingOrderDto(p.id, p.name, p.calorie, count (o), p.price, p.attachment.pressedImage) from Product p join OrderProduct o on p.id = o.product.id where count(o) > 2 group by o.id order by count (o) desc")
    List<TrendingOrderDto> getTrendingProducts();
}