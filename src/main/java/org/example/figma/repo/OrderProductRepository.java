package org.example.figma.repo;

import org.example.figma.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    List<OrderProduct> findAllByOrderId(UUID orderId);
}