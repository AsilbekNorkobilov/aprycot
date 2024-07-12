package org.example.figma.repo;

import org.example.figma.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("from Category c where c.isArchived=false")
    List<Category> findAllByArchivedFalse();
}