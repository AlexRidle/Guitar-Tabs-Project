package com.project.MyProject.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCommentDto {
    private long tabsId;
    private String commentBody;
}
