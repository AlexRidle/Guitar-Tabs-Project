package com.project.MyProject.converter;

import com.project.MyProject.dto.CommentDto;
import com.project.MyProject.entity.Comment;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentConverter implements DtoEntityConverter<CommentDto, Comment> {

    private final UserRepository userRepository;

    public CommentConverter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto convertToDto(final Comment entity) {
        final CommentDto commentDto = new CommentDto();
        commentDto.setCommentBody(entity.getCommentBody());
        commentDto.setId(entity.getId());
        commentDto.setUserId(entity.getUser().getId());
        commentDto.setTabsId(entity.getTabs().getId());
        return commentDto;
    }

    @Override
    public Comment convertToDbo(final CommentDto commentDto) {
        final Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setUser(userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        return comment;
    }
}