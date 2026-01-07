package com.alepaucar.expense_tracker.repository;

import com.alepaucar.expense_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
