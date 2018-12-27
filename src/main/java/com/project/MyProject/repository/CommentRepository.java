package com.project.MyProject.repository;

import com.project.MyProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
}
