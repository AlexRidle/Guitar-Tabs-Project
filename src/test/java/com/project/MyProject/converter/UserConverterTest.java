package com.project.MyProject.converter;

import com.project.MyProject.service.MockData;
import com.project.MyProject.dto.UserDto;
import com.project.MyProject.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserConverterTest {

    private final UserConverter userConverter = new UserConverter();

    @Test
    public void convertToDto() {
        final User user = MockData.user();
        final UserDto userDto = userConverter.convertToDto(user);
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
    }

    @Test
    public void convertToDbo() {
        final UserDto userDto = MockData.userDto();
        final User user = userConverter.convertToDbo(userDto);
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
    }

}
