package com.alepaucar.expense_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private float  amount;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    private LocalDateTime createdAt;

    public Income(String description, float amount, Category category, User user) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    protected Income() {
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
}
