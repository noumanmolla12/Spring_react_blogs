package com.nouman.blog.controller.frontend;

import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Comment;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CategoryRepository;
import com.nouman.blog.repository.CommentRepository;
import com.nouman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/post")
public class FrontendArticleController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CategoryRepository categoryRepository; // ✅ Inject category repo

    @GetMapping("/{slug}")
    public String viewPost(@PathVariable String slug, Model model) {
        Post post = postRepository.findBySlug(slug);
        if (post == null) {
            return "redirect:/";
        }

        // ✅ Only approved comments
        List<Comment> approvedComments = commentRepository.findByPostAndStatus(post, 1);
        post.setComments(approvedComments);

        // ✅ Get all categories for sidebar
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("post", post);
        model.addAttribute("categories", categories); // ✅ Add to model
        return "front/post-view";
    }

    @PostMapping("/{slug}/comment")
    public String submitComment(@PathVariable String slug,
                                 @RequestParam Long postId,
                                 @RequestParam String fullName,
                                 @RequestParam String title,
                                 @RequestParam String description) {

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "redirect:/";
        }

        Comment comment = new Comment();
        comment.setFullName(fullName);
        comment.setTitle(title);
        comment.setDescription(description);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setStatus(0); // Default to pending

        commentRepository.save(comment);

        return "redirect:/post/" + slug;
    }
}