package com.project.MyProject.dto;

import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role = UserRole.USER;
    private Set<TabsDto> tabsSet = new HashSet<>();
}
