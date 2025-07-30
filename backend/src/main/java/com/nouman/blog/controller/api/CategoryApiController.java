package com.nouman.blog.controller.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nouman.blog.dto.CategoryDTO;
import com.nouman.blog.entity.Category;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.PostRepository;


// For integrate with react frotend
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(cat -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setId(cat.getId());
                    dto.setName(cat.getName());
                    dto.setSlug(cat.getSlug());
                    dto.setDescription(cat.getDescription());
                    dto.setActive(cat.isActive()); 
                    dto.setParentId(cat.getParentId());
                    return dto;
                }).collect(Collectors.toList());
    }





    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleCategory(@PathVariable Long id) {
    Optional<Category> optional = categoryRepository.findById(id);
    if (optional.isPresent()) {
        Category cat = optional.get();
        CategoryDTO dto = new CategoryDTO();
        dto.setId(cat.getId());
        dto.setName(cat.getName());
        dto.setSlug(cat.getSlug());
        dto.setDescription(cat.getDescription());
        dto.setParentId(cat.getParentId());
        dto.setActive(cat.isActive());
        return ResponseEntity.ok(dto);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }
}







    
    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto) {
    Category category = new Category();
    category.setName(dto.getName());
    category.setSlug(dto.getSlug());
    category.setDescription(dto.getDescription());
    category.setParentId(dto.getParentId());
    category.setActive(dto.isActive());

    Category saved = categoryRepository.save(category);

    dto.setId(saved.getId());
    return dto;
    }






    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
    Optional<Category> optional = categoryRepository.findById(id);
    if (optional.isPresent()) {
        Category category = optional.get();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setParentId(dto.getParentId());
        category.setActive(dto.isActive());

        Category updated = categoryRepository.save(category);

        // Map updated entity back to DTO
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
        } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

