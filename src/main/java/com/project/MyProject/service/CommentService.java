package com.project.MyProject.service;

import com.project.MyProject.converter.CommentConverter;
import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.dto.comment.CreateCommentDto;
import com.project.MyProject.dto.comment.UpdateCommentDto;
import com.project.MyProject.entity.Comment;
import com.project.MyProject.entity.User;
import com.project.MyProject.enumeration.UserRole;
import com.project.MyProject.repository.CommentRepository;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(final CommentRepository commentRepository, final CommentConverter commentConverter, final UserRepository userRepository) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public String updateComment(final long id, final UpdateCommentDto updateCommentDto, final String username) {
        if (commentRepository.existsById(id)) {
            final User user = userRepository.findByUsername(username);
            final Comment comment = commentRepository.getById(id);
            if (user.getId().equals(comment.getUser().getId())) {
                comment.setCommentBody(updateCommentDto.getCommentBody());
                commentRepository.save(comment);
                return "Comment with id " + id + " has been updated";
            } else return "You can\'t update other comments";
        } else return "Cant find comment with id " + id;
    }

    public String deleteComment(final long id, final String username) {
        if (commentRepository.existsById(id)) {
            final User user = userRepository.findByUsername(username);
            final Comment comment = commentRepository.getById(id);
            if (user.getRole().equals(UserRole.ADMIN)
                    || comment.getUser().getId().equals(user.getId())
                    || comment.getTabs().getId().equals(user.getId())) {
                commentRepository.delete(comment);
                return "Comment with id " + id + " has been deleted";
            } else return "You don\'t have permissions to delete this comment";
        } else return "Cant find comment with id " + id;
    }

    public List<CommentDto> getCommentsOfTab(final long id) {
        return commentRepository.findAllByTabsId(id)
                .stream().map(commentConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public String createComment(final CreateCommentDto createCommentDto, final String username) {
        CommentDto commentDto = new CommentDto();
        commentDto.setTabsId(createCommentDto.getTabsId());
        commentDto.setUserId(userRepository.findByUsername(username).getId());
        commentDto.setCommentBody(createCommentDto.getCommentBody());
        commentRepository.save(commentConverter.convertToDbo(commentDto));
        return "Comment created";
    }
}
