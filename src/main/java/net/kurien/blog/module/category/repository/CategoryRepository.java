package net.kurien.blog.module.category.repository;

import net.kurien.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryKey(String key);
}
