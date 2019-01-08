package com.project.MyProject.dto.user;

import com.project.MyProject.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowUserDto {
    private Long id;
    private String username;
    private String email;
}
