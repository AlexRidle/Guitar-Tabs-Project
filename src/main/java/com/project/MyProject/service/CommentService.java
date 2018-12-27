package com.project.MyProject.service;

import com.project.MyProject.converter.CommentConverter;
import com.project.MyProject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;

    @Autowired
    public CommentService(final CommentRepository commentRepository, final CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
    }


}
