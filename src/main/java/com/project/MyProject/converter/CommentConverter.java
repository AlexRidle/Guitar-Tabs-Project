package com.project.MyProject.converter;

import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.entity.Comment;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentConverter implements DtoEntityConverter<CommentDto, Comment> {

    private final UserRepository userRepository;
    private final TabsRepository tabsRepository;

    public CommentConverter(final UserRepository userRepository, final TabsRepository tabsRepository) {
        this.userRepository = userRepository;
        this.tabsRepository = tabsRepository;
    }

    @Override
    public CommentDto convertToDto(final Comment entity) {
        final CommentDto commentDto = new CommentDto();
        commentDto.setCommentBody(entity.getCommentBody());
        commentDto.setId(entity.getId());
        commentDto.setUserId(entity.getUser().getId());
        commentDto.setUsername(entity.getUser().getUsername());
        commentDto.setTabsId(entity.getTabs().getId());
        return commentDto;
    }

    @Override
    public Comment convertToDbo(final CommentDto commentDto) {
        final Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setTabs(tabsRepository.findById(commentDto.getTabsId()).get());
        return comment;
    }
}