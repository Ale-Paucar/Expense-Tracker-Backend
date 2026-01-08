package com.alepaucar.expense_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private float  amount;
    //relacion unidireccional
    //el join coluimn indica la columna que conteng a la FK, y estas son id de otras entidades
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private LocalDateTime createdAt;

    public Expense(String description, float amount, Category category, User user) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    protected Expense() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
