package com.nouman.blog.controller.admin;

import com.nouman.blog.entity.Comment;
import com.nouman.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/comments")
public class AdminCommentController {

    @Autowired
    private CommentRepository commentRepository;

    // Show list of all comments
    @GetMapping
    public String listComments(Model model) {
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    // Approve comment (status = 1)
    @GetMapping("/approve/{id}")
    public String approveComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setStatus(1);
            commentRepository.save(comment);
        }
        return "redirect:/admin/comments";
    }

    // Unapprove comment (status = 0)
    @GetMapping("/unapprove/{id}")
    public String unapproveComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setStatus(0);
            commentRepository.save(comment);
        }
        return "redirect:/admin/comments";
    }

    // Delete comment
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/comments";
    }
}