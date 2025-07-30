package com.nouman.blog.controller.api;

import com.nouman.blog.dto.CommentDTO;
import com.nouman.blog.entity.Comment;
import com.nouman.blog.entity.Post;
import com.nouman.blog.repository.CommentRepository;
import com.nouman.blog.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // Submit comment
    @PostMapping
    public ResponseEntity<?> submitComment(@RequestBody CommentDTO dto) {
        Optional<Post> postOpt = postRepository.findById(dto.getPostId());
        if (postOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid post ID");
        }

        Comment comment = new Comment();
        comment.setTitle(dto.getTitle());
        comment.setDescription(dto.getDescription());
        comment.setFullName(dto.getFullName());
        comment.setStatus(1); // auto-approved
        comment.setPost(postOpt.get());

        Comment saved = commentRepository.save(comment);
        dto.setId(saved.getId());
        dto.setCreatedAt(saved.getCreatedAt());
        dto.setStatus(saved.getStatus());

        return ResponseEntity.ok(dto);
    }

    // Get approved comments for a post
    @GetMapping("/post/{postId}")
    public List<CommentDTO> getCommentsForPost(@PathVariable Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndStatus(postId, 1);

        return comments.stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setTitle(comment.getTitle());
            dto.setDescription(comment.getDescription());
            dto.setFullName(comment.getFullName());
            dto.setCreatedAt(comment.getCreatedAt());
            dto.setPostId(postId);
            dto.setStatus(comment.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}
