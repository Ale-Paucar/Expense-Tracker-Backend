package com.alepaucar.expense_tracker.repository;

import com.alepaucar.expense_tracker.model.Income;
import com.alepaucar.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Long> {
    List<Income> findByUser(User user);

}
