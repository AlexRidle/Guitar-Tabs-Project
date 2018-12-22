package com.project.MyProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TabsDto {
    private Long id;
    private Long userId;
    private String artist;
    private String title;
    private String tabsBody;
}
