package org.example.figma.repo;

import org.example.figma.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllByArchivedFalse() {
        Category category=Category.builder().isArchived(false).build();
        categoryRepository.save(category);
        List<Category> allByArchivedFalse = categoryRepository.findAllByArchivedFalse();
        Assertions.assertFalse(allByArchivedFalse.isEmpty());
    }
}