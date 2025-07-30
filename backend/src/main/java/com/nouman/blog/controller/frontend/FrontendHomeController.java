package com.nouman.blog.controller.frontend;

import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrontendHomeController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model) {
        // ✅ Fetch 3 featured posts
        List<Post> featuredPosts = postRepository.findTop3ByFeaturedTrueAndActiveTrueOrderByIdDesc();

        // ✅ Fetch 9 latest posts
        List<Post> latestPosts = postRepository.findTop9ByActiveTrueOrderByIdDesc();

        // ✅ All categories for sidebar
        List<Category> categories = categoryRepository.findAll();

        // ✅ Add to model
        model.addAttribute("featuredPosts", featuredPosts);
        model.addAttribute("posts", latestPosts); // still use 'posts' for latest
        model.addAttribute("categories", categories);

        return "front/index";
    }
}