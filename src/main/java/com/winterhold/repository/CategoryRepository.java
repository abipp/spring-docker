package com.winterhold.repository;

import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Page<Category> findByNameContaining(String name, Pageable pageable);

    @Query("""
            SELECT COUNT(cat.name)
            FROM Category AS cat
            WHERE cat.name = :name
            """)
    Long countByCategoryName(@Param("name") String name);
}
