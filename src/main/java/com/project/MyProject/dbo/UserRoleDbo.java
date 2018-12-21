package com.project.MyProject.dbo;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleDbo implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
