package com.nouman.blog.dto;

import java.time.LocalDateTime;

public class PostDTO {

    private Long id;
    private String title;
    private String slug;
    private String content;
    private boolean featured;
    private boolean active;
    private String imageName;
    private LocalDateTime createdAt;
    private Long categoryId; // Reference to Category by ID

    // Constructors
    public PostDTO() {}

    public PostDTO(Long id, String title, String slug, String content, boolean featured,
                   boolean active, String imageName, LocalDateTime createdAt, Long categoryId) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.featured = featured;
        this.active = active;
        this.imageName = imageName;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
