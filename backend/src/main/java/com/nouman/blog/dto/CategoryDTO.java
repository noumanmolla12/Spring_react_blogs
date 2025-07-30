package com.nouman.blog.dto;

public class CategoryDTO {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long parentId;
    private boolean active;

    // Constructors
    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, String slug, String description, Long parentId, boolean active) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parentId = parentId;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}