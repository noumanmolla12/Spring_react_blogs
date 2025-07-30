package com.nouman.blog.controller.admin;
import com.nouman.blog.entity.Category;
import com.nouman.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // List all categories
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    // Show form to add new category
   @GetMapping("/new")
public String showAddForm(Model model) {
    model.addAttribute("category", new Category());
    model.addAttribute("categories", categoryRepository.findAll()); // 👈 Add this
    return "admin/category-form";
}
    // Show form to edit category
   @GetMapping("/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
    model.addAttribute("category", category);
    model.addAttribute("categories", categoryRepository.findAll()); // 👈 Add this
    return "admin/category-form";
}

    // Save category (add or update)
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    // Delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }
}