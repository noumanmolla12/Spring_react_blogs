package com.nouman.blog.repository;

import com.nouman.blog.entity.Comment;
import com.nouman.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ✅ Add this method:
    List<Comment> findByPostIdAndStatus(Long postId, int status);

    // ✅ (Optional) If you use entity instead of ID elsewhere:
    List<Comment> findByPostAndStatus(Post post, int status);
}
