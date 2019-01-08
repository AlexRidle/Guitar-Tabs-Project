package com.project.MyProject.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.MyProject.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
}
