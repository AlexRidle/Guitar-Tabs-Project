package com.project.MyProject.converter;

import com.project.MyProject.dto.user.ShowUserDto;
import com.project.MyProject.dto.user.UserDto;
import com.project.MyProject.entity.User;
import com.project.MyProject.enumeration.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements DtoEntityConverter<UserDto, User> {

    @Override
    public UserDto convertToDto(final User entity) {
        final UserDto userDto = new UserDto();
        BeanUtils.copyProperties(entity, userDto);
        return userDto;
    }

    @Override
    public User convertToDbo(final UserDto userDto) {
        final User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setActive(true);
        if (userDto.getUsername().equals("root"))
            user.setRole(UserRole.ADMIN);
        else user.setRole(UserRole.USER);
        return user;
    }

    public ShowUserDto convertDboToShowUserDto(final User entity) {
        final ShowUserDto showUserDto = new ShowUserDto();
        BeanUtils.copyProperties(entity, showUserDto);
        return showUserDto;
    }

}
