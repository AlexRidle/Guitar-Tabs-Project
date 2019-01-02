package com.project.MyProject.converter;

import com.project.MyProject.dto.UserDto;
import com.project.MyProject.entity.User;
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
        user.setActivationCode("NULL");
        if (userDto.getUsername().equals("root"))
            user.setRole("ROLE_ADMIN");
        else user.setRole("ROLE_USER");
        return user;
    }

}
