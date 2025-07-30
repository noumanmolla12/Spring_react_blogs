package com.nouman.blog.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String title;
    private String description;
    private String fullName;
    private int status;
    private LocalDateTime createdAt;
    private Long postId;

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getPostId() { return postId; }

    public void setPostId(Long postId) { this.postId = postId; }
}
