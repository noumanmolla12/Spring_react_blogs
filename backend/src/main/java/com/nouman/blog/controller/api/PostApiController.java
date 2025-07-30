package com.nouman.blog.controller.api;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nouman.blog.dto.PostDTO;
import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.PostRepository;

// For integrate with react frotend
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/posts")

public class PostApiController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<PostDTO> getAll() {
        return postRepository.findAll().stream().map(post -> {
            PostDTO dto = new PostDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setSlug(post.getSlug());
            dto.setContent(post.getContent());
            dto.setActive(post.isActive());
            dto.setFeatured(post.isFeatured());
            dto.setCategoryId(post.getCategory().getId());
            dto.setImageName(post.getImageName());
            dto.setCreatedAt(post.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }






    @GetMapping("/{id}")
    public ResponseEntity<?> getSinglePost(@PathVariable Long id) {
    Optional<Post> postOpt = postRepository.findById(id);

    if (!postOpt.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Post post = postOpt.get();
    PostDTO dto = new PostDTO();
    dto.setId(post.getId());
    dto.setTitle(post.getTitle());
    dto.setSlug(post.getSlug());
    dto.setContent(post.getContent());
    dto.setActive(post.isActive());
    dto.setFeatured(post.isFeatured());
    dto.setCategoryId(post.getCategory().getId());
    dto.setImageName(post.getImageName());
    dto.setCreatedAt(post.getCreatedAt());

    return ResponseEntity.ok(dto);
    }





    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPostWithImage(
            @RequestPart("post") String postJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        ObjectMapper mapper = new ObjectMapper();
        PostDTO dto;
        try {
            dto = mapper.readValue(postJson, PostDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON: " + e.getMessage());
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().body("Invalid category ID");
        }

        String imageName = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);
                image.transferTo(uploadPath.resolve(imageName).toFile());
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Image upload failed: " + e.getMessage());
            }
        }

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setSlug(dto.getSlug());
        post.setContent(dto.getContent());
        post.setActive(dto.isActive());
        post.setFeatured(dto.isFeatured());
        post.setImageName(imageName);
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());

        Post saved = postRepository.save(post);

        dto.setId(saved.getId());
        dto.setImageName(imageName);
        dto.setCreatedAt(saved.getCreatedAt());

        return ResponseEntity.ok(dto);
    }







    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
public ResponseEntity<?> updatePostWithImage(
        @PathVariable Long id,
        @RequestPart("post") String postJson,
        @RequestPart(value = "image", required = false) MultipartFile image) {

    Optional<Post> existingPostOpt = postRepository.findById(id);
    if (!existingPostOpt.isPresent()) {
        return ResponseEntity.badRequest().body("Post not found with id: " + id);
    }

    ObjectMapper mapper = new ObjectMapper();
    PostDTO dto;
    try {
        dto = mapper.readValue(postJson, PostDTO.class);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Invalid JSON: " + e.getMessage());
    }

    Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
    if (category == null) {
        return ResponseEntity.badRequest().body("Invalid category ID");
    }

    Post existingPost = existingPostOpt.get();
    existingPost.setTitle(dto.getTitle());
    existingPost.setSlug(dto.getSlug());
    existingPost.setContent(dto.getContent());
    existingPost.setActive(dto.isActive());
    existingPost.setFeatured(dto.isFeatured());
    existingPost.setCategory(category);

    // handle new image upload (if provided)
    if (image != null && !image.isEmpty()) {
        try {
            String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            image.transferTo(uploadPath.resolve(imageName).toFile());
            existingPost.setImageName(imageName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Image upload failed: " + e.getMessage());
        }
    }

    Post updated = postRepository.save(existingPost);

    dto.setId(updated.getId());
    dto.setImageName(updated.getImageName());
    dto.setCreatedAt(updated.getCreatedAt());

    return ResponseEntity.ok(dto);
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}