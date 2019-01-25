package com.project.MyProject.dto.tabs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTabsDto {
    private String username;
    private boolean hidden;
    private String artist;
    private String title;
    private String tabsBody;
}
