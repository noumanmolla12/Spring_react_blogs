package com.nouman.blog.repository;

import com.nouman.blog.entity.Category;
import com.nouman.blog.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findBySlug(String slug);

    List<Post> findByActiveTrueOrderByIdDesc();

    List<Post> findByCategoryAndActiveTrueOrderByIdDesc(Category category);

    List<Post> findTop3ByFeaturedTrueAndActiveTrueOrderByIdDesc();

    List<Post> findTop9ByActiveTrueOrderByIdDesc();

    static boolean existByCategory(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existByCategory'");
    }
}