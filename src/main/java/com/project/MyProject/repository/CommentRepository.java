package com.project.MyProject.repository;

import com.project.MyProject.dto.CommentDto;
import com.project.MyProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTabsId(final Long tabs_id);

    Comment getById(final Long id);
}
