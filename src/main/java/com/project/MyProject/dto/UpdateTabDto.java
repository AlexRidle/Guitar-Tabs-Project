package com.project.MyProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTabDto {
    private String artist;
    private String title;
    private String tabsBody;
}
