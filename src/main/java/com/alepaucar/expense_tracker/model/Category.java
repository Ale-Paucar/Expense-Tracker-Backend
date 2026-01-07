package com.alepaucar.expense_tracker.model;

import jakarta.persistence.*;


@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    public Category(String category, CategoryType type) {
        this.category = category;
        this.type = type;
    }

    protected Category(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }
}
