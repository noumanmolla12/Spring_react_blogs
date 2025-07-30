package com.nouman.blog.controller.frontend;

import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class FrontendCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

   @GetMapping("/{id}")
public String viewCategory(@PathVariable Long id, Model model) {
    Category category = categoryRepository.findById(id).orElse(null);
    if (category == null) {
        return "redirect:/"; // fallback if invalid
    }

    List<Post> posts = postRepository.findByCategoryAndActiveTrueOrderByIdDesc(category);
    List<Category> categories = categoryRepository.findAll(); // ✅ ADD THIS

    model.addAttribute("category", category);
    model.addAttribute("posts", posts);
    model.addAttribute("categories", categories); // ✅ ADD THIS

    return "front/post-list-by-category";
}

}