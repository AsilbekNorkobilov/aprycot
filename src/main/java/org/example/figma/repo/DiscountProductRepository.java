package org.example.figma.repo;

import org.example.figma.entity.DiscountProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiscountProductRepository extends JpaRepository<DiscountProduct, UUID> {
}