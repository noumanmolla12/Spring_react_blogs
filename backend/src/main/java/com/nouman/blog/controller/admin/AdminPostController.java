package com.nouman.blog.controller.admin;

import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


import java.nio.file.Path;


@Controller
@RequestMapping("/admin/posts")
public class AdminPostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ Show all posts
    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    // ✅ Show form to add new post
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/post-form";
    }

    // ✅ Show form to edit existing post
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/post-form";
    }

    // ✅ Save post (create/update)
@PostMapping("/save")
public String savePost(@ModelAttribute Post post,
                       @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

    if (!imageFile.isEmpty()) {
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath);

        post.setImageName(fileName);
    }

    postRepository.save(post);
    return "redirect:/admin/posts";
}
    // ✅ Delete post
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/admin/posts";
    }
}