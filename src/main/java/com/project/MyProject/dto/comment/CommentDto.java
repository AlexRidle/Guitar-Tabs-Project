package com.project.MyProject.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long userId;
    private Long tabsId;
    private String commentBody;
}
