package com.project.MyProject.controller;

import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.dto.comment.CreateCommentDto;
import com.project.MyProject.dto.comment.UpdateCommentDto;
import com.project.MyProject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String addComment(@RequestBody final CreateCommentDto createCommentDto) {
        return commentService.createComment(createCommentDto, SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @DeleteMapping("/delete")
    public String deleteComment(@RequestParam final long id){
        return commentService.deleteComment(id,SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @GetMapping("/tab")
    public List<CommentDto> getAllCommentsOfTab(@RequestParam final long id) {
        return commentService.getCommentsOfTab(id);
    }

    @PutMapping("/update")
    public String updateComment(
            @RequestBody final UpdateCommentDto updateCommentDto,
            @RequestParam final long id){
        return commentService.updateComment(id, updateCommentDto, SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }
}
